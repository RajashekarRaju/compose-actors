package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    val movieId by viewModel.isFavoriteMovie.observeAsState()

    val selectedBottomSheet = remember {
        mutableStateOf<BottomSheetType?>(BottomSheetType.MovieDetailBottomSheet)
    }

    val selectBottomSheetCallback = setBottomSheetCallBack(viewModel, selectedBottomSheet)

    UiStateHandler(
        uiState = viewModel.uiState,
    ) { data ->
        MovieDetailsUI(
            data = data,
            actorsSheetUIState = viewModel.sheetUiState,
            movieSheetUIState = viewModel.movieSheetUiState,
            navigateUp = navigateUp,
            selectedBottomSheet = selectedBottomSheet,
            selectBottomSheetCallback = selectBottomSheetCallback,
            isFavoriteMovie = movieId != 0 && movieId != null,
            addMovieToFavorites = { viewModel.addMovieToFavorites(data.movieData) },
            removeMovieFromFavorites = { viewModel.removeMovieFromFavorites(data.movieData) },
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