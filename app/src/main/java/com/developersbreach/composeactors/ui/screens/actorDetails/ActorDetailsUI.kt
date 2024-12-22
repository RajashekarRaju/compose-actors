package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.datasource.fake.fakePersonDetail
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieDetail
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.screens.actorDetails.composables.ActorBackgroundWithGradientForeground
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.FloatingAddToFavoritesButton
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ActorDetailsUI(
    detailUIState: ActorDetailsUIState,
    sheetUIState: ActorDetailsSheetUIState,
    navigateToSelectedMovie: (Int) -> Unit,
    isFavoriteMovie: Boolean,
    navigateUp: () -> Unit,
    getSelectedMovieDetails: (Int) -> Unit,
    addActorToFavorites: () -> Unit,
    removeActorFromFavorites: () -> Unit
) {
    val showFab = rememberSaveable { mutableStateOf(true) }
    val actorProfileUrl = "${detailUIState.actorData?.profileUrl}"
    val modalSheetState = modalBottomSheetState()
    val openActorDetailsBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            SheetContentMovieDetails(
                movie = sheetUIState.selectedMovieDetails,
                navigateToSelectedMovie = navigateToSelectedMovie
            )
        }
    ) {
        ImageBackgroundThemeGenerator(
            imageUrl = actorProfileUrl
        ) {
            Box {
                // Draws gradient from image and overlays on it.
                ActorBackgroundWithGradientForeground(imageUrl = actorProfileUrl)

                Column {
                    // Custom top app bar
                    ActorDetailsTopAppBar(
                        navigateUp = navigateUp,
                        title = "${detailUIState.actorData?.personName}"
                    )

                    // Main details content
                    ActorDetailsContent(
                        navigateUp = navigateUp,
                        detailUIState = detailUIState,
                        openActorDetailsBottomSheet = openActorDetailsBottomSheet,
                        getSelectedMovieDetails = getSelectedMovieDetails,
                        showFab = showFab
                    )
                }
                // Progress bar
                ShowProgressIndicator(isLoadingData = detailUIState.isFetchingDetails)
            }
        }

        if (showFab.value) {
            FloatingAddToFavoritesButton(
                isFavorite = isFavoriteMovie,
                addToFavorites = addActorToFavorites,
                removeFromFavorites = removeActorFromFavorites
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun ActorDetailsUIDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        ActorDetailsUI(
            detailUIState = ActorDetailsUIState(
                castList = fakeMovieList(),
                actorData = fakePersonDetail,
                isFetchingDetails = false
            ),
            sheetUIState = ActorDetailsSheetUIState(fakeMovieDetail),
            navigateToSelectedMovie = {},
            isFavoriteMovie = true,
            navigateUp = {},
            getSelectedMovieDetails = {},
            addActorToFavorites = {},
            removeActorFromFavorites = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActorDetailsUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        ActorDetailsUI(
            detailUIState = ActorDetailsUIState(
                castList = fakeMovieList(),
                actorData = fakePersonDetail,
                isFetchingDetails = false
            ),
            sheetUIState = ActorDetailsSheetUIState(fakeMovieDetail),
            navigateToSelectedMovie = {},
            isFavoriteMovie = true,
            navigateUp = {},
            getSelectedMovieDetails = {},
            addActorToFavorites = {},
            removeActorFromFavorites = {}
        )
    }
}