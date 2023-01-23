package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.data.datasource.network.JsonRemoteData
import com.developersbreach.composeactors.data.datasource.network.NetworkDataSource
import com.developersbreach.composeactors.data.datasource.network.RequestUrls
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.search.SearchRepository
import com.developersbreach.composeactors.ui.screens.search.SearchViewModel
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.utils.NetworkQueryUtils
import kotlinx.coroutines.Job

@Composable
fun HomeScreenUI(
    selectedActor: (Int) -> Unit,
    selectedMovie: (Int) -> Unit,
    openHomeBottomSheet: () -> Job,
    homeUIState: HomeUIState,
    homeSheetUIState: HomeSheetUIState,
    favoriteMovies: List<Movie>,
    searchViewModel: SearchViewModel
) {
    HomeScreenContent(
        selectedActor = selectedActor,
        selectedMovie = selectedMovie,
        openHomeBottomSheet = openHomeBottomSheet,
        homeUIState = homeUIState,
        homeSheetUIState = homeSheetUIState,
        favoriteMovies = favoriteMovies,
        searchViewModel = searchViewModel
    )
}

@Preview
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
                nowPlayingMoviesList = listOf()
            ),
            homeSheetUIState = HomeSheetUIState(
                selectedMovieDetails = null
            ),
            favoriteMovies = emptyList(),
            SearchViewModel(
                SearchRepository(NetworkDataSource(RequestUrls(), JsonRemoteData(RequestUrls()), NetworkQueryUtils()))
            )
        )
    }
}