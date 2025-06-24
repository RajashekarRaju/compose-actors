package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson

@Composable
fun WatchlistScreen(
    watchlistViewModel: WatchlistViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToSelectedPerson: (Int) -> Unit,
) {
    val watchlistMovies = watchlistViewModel.watchlistMovies.collectAsLazyPagingItems()
    val watchlistPersons = watchlistViewModel.watchlistPersons.collectAsLazyPagingItems()

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
        uiEvent = watchlistViewModel.uiEvent,
    )
}