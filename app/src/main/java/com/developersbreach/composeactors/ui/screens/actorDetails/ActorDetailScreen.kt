package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.ShowProgressIndicator
import com.developersbreach.composeactors.ui.components.verticalGradientScrim
import com.developersbreach.composeactors.ui.screens.home.HomeScreen
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.screens.search.SearchScreen


/**
 * Shows details of user selected actor.
 *
 * @param viewModel to manage ui state of [ActorDetailScreen]
 * @param navigateUp navigates user to previous screen.
 *
 * This destination can be accessed from [HomeScreen] & [SearchScreen].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActorDetailScreen(
    selectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
    viewModel: ActorDetailsViewModel
) {
    val uiState = viewModel.uiState
    val sheetUiState = viewModel.sheetUiState
    val actorProfile = "${uiState.actorData?.profileUrl}"

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
                movie = sheetUiState.selectedMovieDetails,
                selectedMovie = selectedMovie
            )
        }
    ) {
        ImageBackgroundThemeGenerator(
            podcastImageUrl = actorProfile
        ) {
            Box {
                // Draws gradient from image and overlays on it.
                ActorBackgroundWithGradientForeground(imageUrl = actorProfile)
                // Main details content
                ActorDetailsContent(navigateUp, viewModel, openActorDetailsBottomSheet)
                // Progress bar
                ShowProgressIndicator(isLoadingData = uiState.isFetchingDetails)
            }
        }
    }
}

/**
 * This image takes up whole space in screen as a background with reduced opacity.
 * On foreground draw vertical gradient so that top elements will be visible.
 *
 * @param imageUrl url to load image with coil.
 */
@Composable
private fun ActorBackgroundWithGradientForeground(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box {
        LoadNetworkImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(R.string.cd_actor_banner),
            shape = RectangleShape,
            showAnimProgress = false,
            modifier = modifier
                .fillMaxSize()
                .alpha(0.5f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalGradientScrim(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
        )
    }
}