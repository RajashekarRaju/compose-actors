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
    navigateUp: () -> Unit
) {
    val movieId by viewModel.isFavoriteMovie.observeAsState()
    UiStateHandler(
        uiState = viewModel.detailUIState
    ) { data ->
        ActorDetailsUI(
            data = data,
            sheetUIState = viewModel.sheetUIState,
            navigateToSelectedMovie = navigateToSelectedMovie,
            isFavoriteMovie = movieId != 0 && movieId != null,
            navigateUp = navigateUp,
            getSelectedMovieDetails = { viewModel.getSelectedMovieDetails(it) },
            addActorToFavorites = { viewModel.addActorToFavorites(data.actorData) },
            removeActorFromFavorites = { viewModel.removeActorFromFavorites(data.actorData) }
        )
    }
}