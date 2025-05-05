package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
internal fun ActorDetailsScreen(
    viewModel: ActorDetailsViewModel = hiltViewModel(),
    navigateToSelectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val movieId by viewModel.isPersonInWatchlist.observeAsState()
    UiStateHandler(
        uiState = viewModel.detailUIState,
    ) { data ->
        ActorDetailsUI(
            data = data,
            sheetUIState = viewModel.sheetUIState,
            navigateToSelectedMovie = navigateToSelectedMovie,
            isInWatchlist = movieId != 0 && movieId != null,
            navigateUp = navigateUp,
            getSelectedMovieDetails = { viewModel.getSelectedMovieDetails(it) },
            addToWatchlist = { viewModel.addActorToWatchlist(data.actorData) },
            removeFromWatchlist = { viewModel.removeActorFromWatchlist(data.actorData) },
        )
    }
}