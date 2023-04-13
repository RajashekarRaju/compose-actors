package com.developersbreach.composeactors.ui.screens.favorites.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(favoriteMovies) { movieItem: Movie ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                LoadNetworkImage(
                    imageUrl = movieItem.bannerUrl,
                    contentDescription = stringResource(R.string.cd_movie_poster),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            getSelectedMovieDetails(movieItem.movieId)
                        }
                )
                Text(
                    text = movieItem.movieName,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .align(Alignment.BottomStart)
                )
                IconButton(
                    onClick = { removeFavoriteMovie(movieItem) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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