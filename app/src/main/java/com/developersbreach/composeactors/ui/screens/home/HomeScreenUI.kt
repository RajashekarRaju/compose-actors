package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.data.model.BottomSheetType
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.screens.search.SearchType
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.Job

@Composable
fun HomeScreenUI(
    selectedActor: (Int) -> Unit,
    selectedMovie: (Int) -> Unit,
    currentBottomSheetCallback: (BottomSheetType) -> Unit,
    openHomeBottomSheet: () -> Job,
    homeUIState: HomeUIState,
    homeSheetUIState: HomeSheetUIState,
    favoriteMovies: List<Movie>,
    updateSearchType: (SearchType) -> Unit
) {
    HomeScreenContent(
        selectedActor = selectedActor,
        selectedMovie = selectedMovie,
        currentBottomSheetCallback = currentBottomSheetCallback,
        openHomeBottomSheet = openHomeBottomSheet,
        homeUIState = homeUIState,
        homeSheetUIState = homeSheetUIState,
        favoriteMovies = favoriteMovies,
        updateSearchType = updateSearchType
    )
}

@Preview
@Composable
private fun HomeScreenUIPreview() {
    ComposeActorsTheme {
        HomeScreenUI(
            selectedActor = {},
            selectedMovie = {},
            currentBottomSheetCallback = {},
            openHomeBottomSheet = { Job() },
            homeUIState = HomeUIState(
                popularActorList = listOf(),
                trendingActorList = listOf(),
                isFetchingActors = false,
                upcomingMoviesList = listOf(),
                nowPlayingMoviesList = listOf()
            ),
            homeSheetUIState = HomeSheetUIState(
                selectedMovieDetails = null
            ),
            favoriteMovies = emptyList(),
            updateSearchType = {}
        )
    }
}