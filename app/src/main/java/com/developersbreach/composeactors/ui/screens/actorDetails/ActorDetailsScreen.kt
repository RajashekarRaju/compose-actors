package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
internal fun ActorDetailsScreen(
    viewModel: ActorDetailsViewModel = hiltViewModel(),
    navigateToSelectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
) {
    UiStateHandler(
        uiState = viewModel.detailUIState,
        isLoading = viewModel.isLoading,
    ) { data ->
        val isPersonInWatchlist by data.isPersonInWatchlist.collectAsState(false)
        ActorDetailsUI(
            data = data,
            uiEvent = viewModel.uiEvent,
            navigateToSelectedMovie = navigateToSelectedMovie,
            isInWatchlist = isPersonInWatchlist,
            navigateUp = navigateUp,
            getSelectedMovieDetails = { viewModel.getSelectedMovieDetails(it) },
            addToWatchlist = { viewModel.addActorToWatchlist(data.actorData) },
            removeFromWatchlist = { viewModel.removeActorFromWatchlist(data.actorData) },
        )
    }
}