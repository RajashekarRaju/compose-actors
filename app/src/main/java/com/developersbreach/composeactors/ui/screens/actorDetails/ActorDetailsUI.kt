package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakePersonDetail
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieDetail
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.screens.actorDetails.composables.ActorBackgroundWithGradientForeground
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.FloatingAddToWatchlistButton
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@Composable
internal fun ActorDetailsUI(
    data: ActorDetailsData,
    sheetUIState: ActorDetailsSheetUIState,
    navigateToSelectedMovie: (Int) -> Unit,
    isInWatchlist: Boolean,
    navigateUp: () -> Unit,
    getSelectedMovieDetails: (Int) -> Unit,
    addToWatchlist: () -> Unit,
    removeFromWatchlist: () -> Unit,
) {
    val showFab = rememberSaveable { mutableStateOf(true) }
    val actorProfileUrl = "${data.actorData?.profileUrl}"
    val modalSheetState = modalBottomSheetState()
    val openActorDetailsBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState,
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            SheetContentMovieDetails(
                movie = sheetUIState.selectedMovieDetails,
                navigateToSelectedMovie = navigateToSelectedMovie,
            )
        },
    ) {
        ImageBackgroundThemeGenerator(
            imageUrl = actorProfileUrl,
        ) {
            Box {
                // Draws gradient from image and overlays on it.
                ActorBackgroundWithGradientForeground(imageUrl = actorProfileUrl)

                Column {
                    // Custom top app bar
                    ActorDetailsTopAppBar(
                        navigateUp = navigateUp,
                        title = "${data.actorData?.personName}",
                    )

                    // Main details content
                    ActorDetailsContent(
                        navigateUp = navigateUp,
                        data = data,
                        openActorDetailsBottomSheet = openActorDetailsBottomSheet,
                        getSelectedMovieDetails = getSelectedMovieDetails,
                        showFab = showFab,
                    )
                }
                // Progress bar
                ShowProgressIndicator(isLoadingData = data.isFetchingDetails)
            }
        }

        if (showFab.value) {
            FloatingAddToWatchlistButton(
                isInWatchlist = isInWatchlist,
                addToWatchlist = addToWatchlist,
                removeFromWatchlist = removeFromWatchlist,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ActorDetailsUIPreview() {
    ComposeActorsTheme {
        ActorDetailsUI(
            data = ActorDetailsData(
                castList = fakeMovieList(),
                actorData = fakePersonDetail,
                isFetchingDetails = false,
            ),
            sheetUIState = ActorDetailsSheetUIState(fakeMovieDetail),
            navigateToSelectedMovie = {},
            isInWatchlist = true,
            navigateUp = {},
            getSelectedMovieDetails = {},
            addToWatchlist = {},
            removeFromWatchlist = {},
        )
    }
}