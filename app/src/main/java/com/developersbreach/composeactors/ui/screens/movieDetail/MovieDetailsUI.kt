package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieCastList
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieDetail
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.ui.animations.LayerRevealImage
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentActorDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieProviders
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.Job


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieDetailsUI(
    modifier: Modifier = Modifier,
    uiState: MovieDetailsUIState,
    actorsSheetUIState: ActorsSheetUIState,
    movieSheetUIState: MovieSheetUIState,
    navigateUp: () -> Unit,
    selectedBottomSheet: MutableState<BottomSheetType?>,
    selectBottomSheetCallback: (BottomSheetType) -> Unit,
    isFavoriteMovie: Boolean,
    addMovieToFavorites: () -> Unit,
    removeMovieFromFavorites: () -> Unit,
    navigateToSelectedMovie: (movieId: Int) -> Unit
) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    val modalSheetState = modalBottomSheetState(
        animationSpec = tween(durationMillis = 300, delayMillis = 50)
    )

    val openMovieDetailsBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState
    )

    // This helps us reveal screen content with fadeIn anim once reveal effect is completed.
    val isLayerRevealAnimationEnded = rememberSaveable { mutableStateOf(false) }
    // Change button state with respect to scroll changes.
    val showFab = rememberSaveable { mutableStateOf(true) }
    // Remember scroll state to change button state.
    val showBottomSheetScaffold = rememberSaveable { mutableStateOf(!isLayerRevealAnimationEnded.value) }

    val bottomSheetPeakValue = if (showBottomSheetScaffold.value) {
        72.dp
    } else {
        0.dp
    }
    val animatedScaffoldSheetPeekHeight = getAnimatedSheetPeekHeight(bottomSheetPeakValue)

    // Sheet content contains details for the selected movie from list.
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetContent = {
            GetBottomSheetContent(
                selectedBottomSheet.value,
                actorsSheetUIState,
                movieSheetUIState,
                navigateToSelectedMovie
            )
        },
    ) {
        val scaffoldState = rememberBottomSheetScaffoldState()
        BottomSheetScaffold(
            modifier = Modifier.systemBarsPadding(),
            scaffoldState = scaffoldState,
            sheetPeekHeight = animatedScaffoldSheetPeekHeight,
            sheetContent = {
                SheetContentMovieProviders(
                    movieProvider = uiState.movieProviders,
                    isFavoriteMovie = isFavoriteMovie,
                    addMovieToFavorites = addMovieToFavorites,
                    removeMovieFromFavorites = removeMovieFromFavorites,
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("TestTag:MovieDetailScreen")
            ) {
                MovieDetailsUiContent(
                    uiState = uiState,
                    isLayerRevealAnimationEnded = isLayerRevealAnimationEnded,
                    state = state,
                    modifier = modifier,
                    navigateUp = navigateUp,
                    selectBottomSheetCallback = selectBottomSheetCallback,
                    openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                    showFab = showFab,
                    showBottomSheetScaffold = showBottomSheetScaffold,
                )
            }
        }
    }
}

@Composable
private fun getAnimatedSheetPeekHeight(bottomSheetPeakValue: Dp): Dp {
    val transition = updateTransition(targetState = bottomSheetPeakValue, label = "")
    val animatedSheetPeekHeight by transition.animateDp(
        transitionSpec = {
            spring(stiffness = Spring.StiffnessLow)
        }, label = ""
    ) { value -> value }
    return animatedSheetPeekHeight
}

@Composable
fun MovieDetailsUiContent(
    uiState: MovieDetailsUIState,
    isLayerRevealAnimationEnded: MutableState<Boolean>,
    state: MutableTransitionState<Boolean>,
    modifier: Modifier,
    navigateUp: () -> Unit,
    selectBottomSheetCallback: (BottomSheetType) -> Unit,
    openMovieDetailsBottomSheet: () -> Job,
    showFab: MutableState<Boolean>,
    showBottomSheetScaffold: MutableState<Boolean>
) {
    // Background poster with layer reveal effect
    LayerRevealImage(uiState.movieData?.poster, isLayerRevealAnimationEnded)
    // Fade enter animation detail screen once layer reveal completes
    if (isLayerRevealAnimationEnded.value) {
        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn()
        ) {
            // Main details content
            MovieDetailsContent(
                modifier = modifier,
                uiState = uiState,
                navigateUp = navigateUp,
                showFab = showFab,
                openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
                selectBottomSheetCallback = selectBottomSheetCallback,
                showBottomSheetScaffold = showBottomSheetScaffold
            )
        }
    }
}

@Composable
private fun GetBottomSheetContent(
    bottomSheetType: BottomSheetType?,
    sheetUiState: ActorsSheetUIState,
    movieSheetUIState: MovieSheetUIState,
    navigateToSelectedMovie: (Int) -> Unit
) {
    bottomSheetType?.let { type ->
        when (type) {
            BottomSheetType.MovieDetailBottomSheet -> {
                SheetContentMovieDetails(
                    movie = movieSheetUIState.selectedMovieDetails,
                    navigateToSelectedMovie = navigateToSelectedMovie
                )
            }
            BottomSheetType.ActorDetailBottomSheet -> {
                SheetContentActorDetails(
                    actor = sheetUiState.selectedPersonDetails
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        MovieDetailsContent(
            uiState = MovieDetailsUIState(
                movieData = fakeMovieDetail,
                similarMovies = fakeMovieList(),
                recommendedMovies = fakeMovieList(),
                movieCast = fakeMovieCastList(),
                isFetchingDetails = false,
                movieProviders = listOf(Flatrate("", 1, ""))
            ),
            navigateUp = {},
            showFab = remember { mutableStateOf(true) },
            openMovieDetailsBottomSheet = { Job() },
            selectBottomSheetCallback = {},
            showBottomSheetScaffold = remember { mutableStateOf(true) }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun MovieDetailsUIDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        MovieDetailsContent(
            uiState = MovieDetailsUIState(
                movieData = fakeMovieDetail,
                similarMovies = fakeMovieList(),
                recommendedMovies = fakeMovieList(),
                movieCast = fakeMovieCastList(),
                isFetchingDetails = false,
                movieProviders = listOf(Flatrate("", 1, ""))
            ),
            navigateUp = {},
            showFab = remember { mutableStateOf(true) },
            openMovieDetailsBottomSheet = { Job() },
            selectBottomSheetCallback = {},
            showBottomSheetScaffold = remember { mutableStateOf(true) }
        )
    }
}