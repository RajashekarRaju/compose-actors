package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.developersbreach.composeactors.ui.screens.home.HomeScreen
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
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
        }
    )
}