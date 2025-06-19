package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navigateToSelectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val selectedBottomSheet = remember {
        mutableStateOf<BottomSheetType?>(BottomSheetType.MovieDetailBottomSheet)
    }

    val selectBottomSheetCallback = setBottomSheetCallBack(viewModel, selectedBottomSheet)

    UiStateHandler(
        uiState = viewModel.uiState,
        isLoading = viewModel.isLoading,
    ) { data ->
        val isMovieInWatchlist by data.isMovieInWatchlist.collectAsState(false)
        MovieDetailsUI(
            data = data,
            uiEvent = viewModel.uiEvent,
            actorsSheetUIState = viewModel.sheetUiState,
            movieSheetUIState = viewModel.movieSheetUiState,
            navigateUp = navigateUp,
            selectedBottomSheet = selectedBottomSheet,
            selectBottomSheetCallback = selectBottomSheetCallback,
            isMovieInWatchlist = isMovieInWatchlist,
            addMovieToWatchlist = { viewModel.addMovieToWatchlist(data.movieData) },
            removeMovieFromWatchlist = { viewModel.removeMovieFromWatchlist(data.movieData) },
            navigateToSelectedMovie = navigateToSelectedMovie,
        )
    }
}

private fun setBottomSheetCallBack(
    viewModel: MovieDetailViewModel,
    selectedBottomSheet: MutableState<BottomSheetType?>,
) = { bottomSheetType: BottomSheetType ->
    when (bottomSheetType) {
        BottomSheetType.MovieDetailBottomSheet -> {
            viewModel.getSelectedMovieDetails(bottomSheetType.movieOrPersonId)
        }
        BottomSheetType.ActorDetailBottomSheet -> {
            viewModel.getSelectedPersonDetails(bottomSheetType.movieOrPersonId)
        }
    }
    selectedBottomSheet.value = bottomSheetType
}