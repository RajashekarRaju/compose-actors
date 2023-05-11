package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.data.model.BottomSheetType
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.Job


@Composable
fun MovieDetailsUI(
    modifier: Modifier = Modifier,
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
        MovieDetailsContent(
            modifier = modifier,
            uiState = uiState,
            navigateUp = navigateUp,
            showFab = showFab,
            openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
            selectBottomSheetCallback = selectBottomSheetCallback
        )
    }
}

@Preview
@Composable
private fun MovieDetailsUIPreview() {
    ComposeActorsTheme {
        MovieDetailsUI(
            uiState = MovieDetailsUIState(
                movieData = null,
                similarMovies = listOf(),
                recommendedMovies = listOf(),
                movieCast = listOf(),
                isFetchingDetails = false
            ),
            navigateUp = {},
            showFab = rememberSaveable { mutableStateOf(true) },
            openMovieDetailsBottomSheet = { Job() },
            selectBottomSheetCallback = { }
        )
    }
}