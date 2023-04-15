package com.developersbreach.composeactors.ui.screens.favorites.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.screens.favorites.NoFavoritesFoundUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme


/**
 * Content shown for home tab Favorites.
 * If user did not add any movies to favorites, a message will be shown.
 */
@Composable
fun FavoriteMoviesTabContent(
    getSelectedMovieDetails: (Int) -> Unit,
    favoriteMovies: List<Movie>,
    removeFavoriteMovie: (Movie) -> Unit,
) {
    if (favoriteMovies.isEmpty()) {
        NoFavoritesFoundUI()
    }

    val listState = rememberLazyListState()

    LazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        state = listState,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(favoriteMovies) { movieItem: Movie ->
            ItemFavoriteMovie(
                movieItem = movieItem,
                getSelectedMovieDetails = getSelectedMovieDetails,
                removeFavoriteMovie = removeFavoriteMovie
            )
        }
    }
}

@Composable
private fun ItemFavoriteMovie(
    movieItem: Movie,
    getSelectedMovieDetails: (Int) -> Unit,
    removeFavoriteMovie: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface)
    ) {
        LoadNetworkImage(
            imageUrl = movieItem.bannerUrl,
            contentDescription = stringResource(R.string.cd_movie_poster),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable { getSelectedMovieDetails(movieItem.movieId) }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = movieItem.movieName,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary,
                lineHeight = 20.sp,
                modifier = Modifier.weight(0.8f)
            )
            IconButton(
                onClick = { removeFavoriteMovie(movieItem) },
                modifier = Modifier.weight(0.1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun FavoriteMoviesTabContentPreview() {
    ComposeActorsTheme {
        FavoriteMoviesTabContent(
            getSelectedMovieDetails = {},
            favoriteMovies = listOf(),
            removeFavoriteMovie = {}
        )
    }
}