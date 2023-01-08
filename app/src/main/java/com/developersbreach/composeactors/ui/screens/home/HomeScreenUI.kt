package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.Composable
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreenUI(
    selectedActor: (Int) -> Unit,
    selectedMovie: (Int) -> Unit,
    openHomeBottomSheet: () -> Job,
    homeUIState: HomeUIState,
    homeSheetUIState: HomeSheetUIState,
    favoriteMovies: List<Movie>,
) {
    HomeScreenContent(
        selectedActor = selectedActor,
        selectedMovie = selectedMovie,
        openHomeBottomSheet = openHomeBottomSheet,
        homeUIState = homeUIState,
        homeSheetUIState = homeSheetUIState,
        favoriteMovies = favoriteMovies
    )
}

@Composable
private fun HomeScreenUIPreview() {
    ComposeActorsTheme {
        HomeScreenUI(
            selectedActor = {},
            selectedMovie = {},
            openHomeBottomSheet = { Job() },
            homeUIState = HomeUIState(
                popularActorList = listOf(),
                trendingActorList = listOf(),
                isFetchingActors = false,
                upcomingMoviesList = listOf(),
                nowPlayingMoviesList = emptyFlow()
            ),
            homeSheetUIState = HomeSheetUIState(
                selectedMovieDetails = null
            ),
            favoriteMovies = emptyList()
        )
    }
}