package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.screens.home.HomeScreen


/**
 * Screen shows details for the selected movie.
 * This destination can be accessed from [HomeScreen] & [ActorDetailsScreen].
 */
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    navigateToSelectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit
) {
    val uiState = viewModel.uiState
    val actorsSheetUIState = viewModel.sheetUiState
    val movieSheetUIState = viewModel.movieSheetUiState

    val movieId by viewModel.isFavoriteMovie.observeAsState()

    val selectedBottomSheet = remember {
        mutableStateOf<BottomSheetType?>(BottomSheetType.MovieDetailBottomSheet)
    }

    val selectBottomSheetCallback = setBottomSheetCallBack(viewModel, selectedBottomSheet)

    MovieDetailsUI(
        uiState = uiState,
        actorsSheetUIState = actorsSheetUIState,
        movieSheetUIState = movieSheetUIState,
        navigateUp = navigateUp,
        selectedBottomSheet = selectedBottomSheet,
        selectBottomSheetCallback = selectBottomSheetCallback,
        isFavoriteMovie = movieId != 0 && movieId != null,
        addMovieToFavorites = { viewModel.addMovieToFavorites() },
        removeMovieFromFavorites = { viewModel.removeMovieFromFavorites() },
        navigateToSelectedMovie = navigateToSelectedMovie
    )
}

private fun setBottomSheetCallBack(
    viewModel: MovieDetailViewModel,
    selectedBottomSheet: MutableState<BottomSheetType?>
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
