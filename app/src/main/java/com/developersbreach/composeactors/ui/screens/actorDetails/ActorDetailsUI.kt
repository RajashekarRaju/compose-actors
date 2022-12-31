package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.screens.actorDetails.composables.ActorBackgroundWithGradientForeground
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ActorDetailsUI(
    detailUIState: ActorDetailsUIState,
    sheetUIState: ActorDetailsSheetUIState,
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

                Column {
                    // Custom top app bar
                    ActorDetailsTopAppBar(
                        navigateUp = navigateUp,
                        title = "${detailUIState.actorData?.actorName}"
                    )

                    // Main details content
                    ActorDetailsContent(
                        navigateUp = navigateUp,
                        detailUIState = detailUIState,
                        openActorDetailsBottomSheet = openActorDetailsBottomSheet,
                        getSelectedMovieDetails = getSelectedMovieDetails
                    )
                }
                // Progress bar
                ShowProgressIndicator(isLoadingData = detailUIState.isFetchingDetails)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ActorDetailsUIPreview() {
    ComposeActorsTheme {
        ActorDetailsUI(
            detailUIState = ActorDetailsUIState(
                castList = listOf(),
                actorData = null,
                isFetchingDetails = false
            ),
            sheetUIState = ActorDetailsSheetUIState(
                selectedMovieDetails = null
            ),
            actorProfileUrl = "",
            modalSheetState = modalBottomSheetState(),
            selectedMovie = {},
            navigateUp = {},
            openActorDetailsBottomSheet = { Job() },
            getSelectedMovieDetails = {}
        )
    }
}