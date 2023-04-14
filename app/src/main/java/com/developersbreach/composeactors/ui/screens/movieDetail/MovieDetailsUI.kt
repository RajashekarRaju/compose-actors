package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.data.model.BottomSheetType
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.Job


@Composable
fun MovieDetailsUI(
    uiState: MovieDetailsUIState,
    navigateUp: () -> Unit,
    showFab: MutableState<Boolean>,
    openMovieDetailsBottomSheet: () -> Job,
    selectBottomSheetCallback: (BottomSheetType) -> Unit
) {
    MovieDetailsContent(
        uiState = uiState,
        navigateUp = navigateUp,
        showFab = showFab,
        openMovieDetailsBottomSheet = openMovieDetailsBottomSheet,
        selectBottomSheetCallback = selectBottomSheetCallback
    )
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