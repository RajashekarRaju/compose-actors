package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

/**
 * @param movieList searchable results row list elements of [Movie]
 */
@Composable
fun MovieSearchUI(
    movieList: List<Movie>,
    selectedMovie: (Int) -> Unit,
    closeKeyboard: () -> Unit?
) {
    LazyColumn(
        // This padding helps avoid content going behind the navigation bars.
        modifier = Modifier.padding(bottom = 48.dp)
    ) {
        items(movieList) { movie ->
            ItemSearchMovie(movie, selectedMovie, closeKeyboard)
        }
    }
}

/**
 * @param selectedMovie navigate to actor [ActorDetailsScreen] from user selected movie.
 */
@Composable
private fun ItemSearchMovie(
    movie: Movie,
    selectedMovie: (Int) -> Unit,
    closeKeyboard: () -> Unit?
) {
    Text(
        text = movie.movieName,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                closeKeyboard()
                selectedMovie(movie.movieId)
            }
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .wrapContentWidth(Alignment.Start)
    )
}

@Preview
@Composable
private fun MovieSearchUIPreview() {
    ComposeActorsTheme {
        MovieSearchUI(
            movieList = listOf(),
            selectedMovie = {},
            closeKeyboard = {}
        )
    }
}