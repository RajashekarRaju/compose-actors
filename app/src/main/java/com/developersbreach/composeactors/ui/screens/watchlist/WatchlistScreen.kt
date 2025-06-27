package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun WatchlistScreen(
    watchlistViewModel: WatchlistViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToSelectedPerson: (Int) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = watchlistViewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = watchlistViewModel.uiEvent,
        isLoading = watchlistViewModel.isLoading,
    ) { data ->
        WatchlistScreenUI(
            navigateUp = navigateUp,
            navigateToSelectedMovie = navigateToSelectedMovie,
            removeMovieFromWatchlist = { watchlistViewModel.removeMovieFromWatchlist(it) },
            navigateToSelectedPerson = navigateToSelectedPerson,
            removeWatchlistPerson = { watchlistViewModel.removePersonFromWatchlist(it) },
            data = data,
            scaffoldState = scaffoldState,
        )
    }
}