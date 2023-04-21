package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.developersbreach.composeactors.ui.screens.home.HomeScreen
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.FloatingAddToFavoritesButton
import com.developersbreach.composeactors.ui.screens.search.SearchScreen


/**
 * Shows details of user selected actor.
 *
 * @param viewModel to manage ui state of [ActorDetailsScreen]
 * @param navigateUp navigates user to previous screen.
 *
 * This destination can be accessed from [HomeScreen] & [SearchScreen].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ActorDetailsScreen(
    selectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
    viewModel: ActorDetailsViewModel
) {
    val detailUIState = viewModel.detailUIState
    val sheetUIState = viewModel.sheetUIState
    val actorProfileUrl = "${detailUIState.actorData?.profileUrl}"

    val modalSheetState = modalBottomSheetState()
    val openActorDetailsBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState
    )

    val showFab = rememberSaveable { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        ActorDetailsUI(
            detailUIState = detailUIState,
            sheetUIState = sheetUIState,
            actorProfileUrl = actorProfileUrl,
            modalSheetState = modalSheetState,
            selectedMovie = selectedMovie,
            navigateUp = navigateUp,
            openActorDetailsBottomSheet = openActorDetailsBottomSheet,
            getSelectedMovieDetails = { movieId ->
                viewModel.getSelectedMovieDetails(movieId)
            },
            showFab = showFab
        )

        val movieId by viewModel.isFavoriteMovie.observeAsState()
        val isFavoriteMovie = movieId != 0 && movieId != null

        if (showFab.value) {
            FloatingAddToFavoritesButton(
                isFavoriteMovie,
                { viewModel.addActorToFavorites() },
                { viewModel.removeActorFromFavorites() }
            )
        }
    }
}