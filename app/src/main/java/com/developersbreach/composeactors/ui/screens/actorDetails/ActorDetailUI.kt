package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.screens.actorDetails.composable.ActorBackgroundWithGradientForeground
import com.developersbreach.composeactors.ui.screens.actorDetails.composable.ActorDetailsContent
import com.developersbreach.composeactors.ui.screens.actorDetails.data.model.DetailsUIState
import com.developersbreach.composeactors.ui.screens.actorDetails.data.model.SheetUIState
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActorDetailUI(
    detailUIState: DetailsUIState,
    sheetUIState: SheetUIState,
    actorProfileUrl: String,
    modalSheetState: ModalBottomSheetState,
    selectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
    openActorDetailsBottomSheet: () -> Job,
    getSelectedMovieDetails: (Int) -> Unit
) {

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            SheetContentMovieDetails(
                movie = sheetUIState.selectedMovieDetails,
                selectedMovie = selectedMovie
            )
        }
    ) {
        ImageBackgroundThemeGenerator(
            podcastImageUrl = actorProfileUrl
        ) {
            Box {
                // Draws gradient from image and overlays on it.
                ActorBackgroundWithGradientForeground(imageUrl = actorProfileUrl)
                // Main details content
                ActorDetailsContent(
                    navigateUp,
                    detailUIState,
                    openActorDetailsBottomSheet,
                    getSelectedMovieDetails
                )
                // Progress bar
                ShowProgressIndicator(isLoadingData = detailUIState.isFetchingDetails)
            }
        }
    }
}