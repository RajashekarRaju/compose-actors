package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    navigateToSelectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val selectedBottomSheet = remember {
        mutableStateOf<BottomSheetType?>(BottomSheetType.MovieDetailBottomSheet)
    }

    val selectBottomSheetCallback = setBottomSheetCallBack(viewModel, selectedBottomSheet)
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = viewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = viewModel.uiEvent,
        isLoading = viewModel.isLoading,
    ) { data ->
        val isMovieInWatchlist by data.isMovieInWatchlist.collectAsState(false)
        MovieDetailsUI(
            data = data,
            scaffoldState = scaffoldState,
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
    viewModel: MovieDetailsViewModel,
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