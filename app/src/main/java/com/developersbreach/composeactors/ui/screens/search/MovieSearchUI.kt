package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaTextH6

/**
 * @param movieList searchable results row list elements of [Movie]
 */
@Composable
fun MovieSearchUI(
    movieList: List<Movie>,
    navigateToSelectedMovie: (Int) -> Unit,
    closeKeyboard: () -> Unit?,
) {
    LazyColumn(
        // This padding helps avoid content going behind the navigation bars.
        modifier = Modifier.padding(bottom = 48.dp),
    ) {
        items(movieList) { movie ->
            ItemSearchMovie(
                movie = movie,
                onClickMovie = navigateToSelectedMovie,
                closeKeyboard = closeKeyboard,
            )
        }
    }
}

/**
 * @param onClickMovie navigate to actor [ActorDetailsScreen] from user selected movie.
 */
@Composable
private fun ItemSearchMovie(
    movie: Movie,
    onClickMovie: (Int) -> Unit,
    closeKeyboard: () -> Unit?,
) {
    CaTextH6(
        text = movie.movieName,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                closeKeyboard()
                onClickMovie(movie.movieId)
            }
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .wrapContentWidth(Alignment.Start),
    )
}

@PreviewLightDark
@Composable
private fun MovieSearchUIPreview() {
    ComposeActorsTheme {
        MovieSearchUI(
            movieList = fakeMovieList(),
            navigateToSelectedMovie = {},
            closeKeyboard = {},
        )
    }
}