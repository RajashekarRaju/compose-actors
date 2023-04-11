package com.developersbreach.composeactors.ui.screens.favorites.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
) {
    if (favoriteMovies.isEmpty()) {
        NoFavoritesFoundUI()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 16.dp)
    ) {
        items(
            items = favoriteMovies,
            key = { it.movieId }
        ) { movieItem ->
            LoadNetworkImage(
                imageUrl = movieItem.posterPathUrl,
                contentDescription = stringResource(R.string.cd_movie_poster),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .size(120.dp, 180.dp)
                    .clickable {
                        getSelectedMovieDetails(movieItem.movieId)
                    }
            )
        }
    }
}

@Preview
@Composable
private fun FavoriteMoviesTabContentPreview() {
    ComposeActorsTheme {
        FavoriteMoviesTabContent(
            getSelectedMovieDetails = {},
            favoriteMovies = listOf()
        )
    }
}