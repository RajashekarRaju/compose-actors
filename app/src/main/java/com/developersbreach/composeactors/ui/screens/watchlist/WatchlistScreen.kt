package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.data.person.model.WatchlistPerson
import com.developersbreach.composeactors.data.movie.model.Movie

@Composable
fun WatchlistScreen(
    watchlistViewModel: WatchlistViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToSelectedPerson: (Int) -> Unit,
) {
    val watchlistMovies by watchlistViewModel.watchlistMovies.observeAsState(emptyList())
    val watchlistPersons by watchlistViewModel.watchlistPersons.observeAsState(emptyList())

    val removeMovieFromWatchlist = { movie: Movie ->
        watchlistViewModel.removeMovieFromWatchlist(movie)
    }

    val removeWatchlistPerson = { person: WatchlistPerson ->
        watchlistViewModel.removePersonFromWatchlist(person)
    }

    WatchlistScreenUI(
        navigateUp = navigateUp,
        watchlistMovies = watchlistMovies,
        navigateToSelectedMovie = navigateToSelectedMovie,
        removeMovieFromWatchlist = removeMovieFromWatchlist,
        navigateToSelectedPerson = navigateToSelectedPerson,
        watchlistPersons = watchlistPersons,
        removeWatchlistPerson = removeWatchlistPerson,
    )
}