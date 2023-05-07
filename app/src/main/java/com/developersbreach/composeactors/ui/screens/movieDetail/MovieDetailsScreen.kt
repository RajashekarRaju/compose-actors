package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.BottomSheetType
import com.developersbreach.composeactors.ui.animations.LayerRevealImage
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.screens.home.HomeScreen
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentActorDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.screens.movieDetail.composables.FloatingAddToFavoritesButton
import kotlinx.coroutines.Job


/**
 * Screen shows details for the selected movie.
 * This destination can be accessed from [HomeScreen] & [ActorDetailsScreen].
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieDetailScreen(
    selectedMovie: (Int) -> Unit,
    viewModel: MovieDetailViewModel,
    navigateUp: () -> Unit
) {
    val uiState = viewModel.uiState
    val sheetUiState = viewModel.sheetUiState
    val movieSheetUIState = viewModel.movieSheetUiState
    // This helps us reveal screen content with fadeIn anim once reveal effect is completed.
    val isLayerRevealAnimationEnded = rememberSaveable { mutableStateOf(false) }
    // Change button state with respect to scroll changes.
    val showFab = rememberSaveable { mutableStateOf(true) }
    val movieId by viewModel.isFavoriteMovie.observeAsState()

    val modalSheetState = modalBottomSheetState(
        animationSpec = tween(durationMillis = 300, delayMillis = 50)
    )

    val selectedBottomSheet =
        remember { mutableStateOf<BottomSheetType?>(BottomSheetType.MovieDetailBottomSheet) }

    val selectBottomSheetCallback = setBottomSheetCallBack(viewModel, selectedBottomSheet)

    val openMovieDetailsBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState
    )

    // Sheet content contains details for the selected movie from list.
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            GetBottomSheetContent(
                selectedBottomSheet.value,
                sheetUiState,
                movieSheetUIState,
                selectedMovie
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background poster with layer reveal effect
            LayerRevealImage(uiState.movieData?.poster, isLayerRevealAnimationEnded)
            // Fade enter animation detail screen once layer reveal completes
            if (isLayerRevealAnimationEnded.value) {
                AnimateMovieDetailScreenContent(
                    uiState = uiState,
                    navigateUp = navigateUp,
                    showFab = showFab,
                    openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                    selectBottomSheetCallback = selectBottomSheetCallback
                )
            }
            // Progress bar - Hidden temporarily, although it works fine cannot have it in current
            // screen placement since it is on top of reveal animation.
            // ShowProgressIndicator(isLoadingData = uiState.isFetchingDetails)
            if (showFab.value) {
                FloatingAddToFavoritesButton(
                    isFavorite = movieId != 0 && movieId != null,
                    addToFavorites = { viewModel.addMovieToFavorites() },
                    removeFromFavorites = { viewModel.removeMovieFromFavorites() }
                )
            }
        }
    }
}

@Composable
private fun GetBottomSheetContent(
    bottomSheetType: BottomSheetType?,
    sheetUiState: ActorsSheetUIState,
    movieSheetUIState: MovieSheetUIState,
    selectedMovie: (Int) -> Unit
) {
    bottomSheetType?.let { type ->
        when (type) {
            BottomSheetType.MovieDetailBottomSheet -> {
                SheetContentMovieDetails(
                    movieSheetUIState.selectedMovieDetails,
                    selectedMovie
                )
            }
            BottomSheetType.ActorDetailBottomSheet -> {
                SheetContentActorDetails(actor = sheetUiState.selectedActorDetails)
            }
        }
    }
}

private fun setBottomSheetCallBack(
    viewModel: MovieDetailViewModel,
    selectedBottomSheet: MutableState<BottomSheetType?>
) = { bottomSheetType: BottomSheetType ->
    when (bottomSheetType) {
        BottomSheetType.MovieDetailBottomSheet -> {
            viewModel.getSelectedMovieDetails(bottomSheetType.movieOrActorId)
        }
        BottomSheetType.ActorDetailBottomSheet -> {
            viewModel.getSelectedActorDetails(bottomSheetType.movieOrActorId)
        }
    }
    selectedBottomSheet.value = bottomSheetType
}

@Composable
private fun AnimateMovieDetailScreenContent(
    uiState: MovieDetailsUIState,
    navigateUp: () -> Unit,
    showFab: MutableState<Boolean>,
    openMovieDetailsBottomSheet: () -> Job,
    selectBottomSheetCallback: (BottomSheetType) -> Unit
) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn()
    ) {
        // Main details content
        MovieDetailsUI(
            uiState = uiState,
            navigateUp = navigateUp,
            showFab = showFab,
            openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
            selectBottomSheetCallback = selectBottomSheetCallback
        )
    }
}
