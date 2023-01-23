package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.screens.home.composables.HomeTabsContainer
import com.developersbreach.composeactors.ui.screens.home.tabs.ActorsTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.FavoritesTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.MoviesTabContent
import com.developersbreach.composeactors.ui.screens.search.SearchViewModel
import com.developersbreach.composeactors.utils.SearchType
import kotlinx.coroutines.Job


// Main content for the home screen.
@Composable
fun HomeScreenContent(
    selectedActor: (Int) -> Unit,
    selectedMovie: (Int) -> Unit,
    openHomeBottomSheet: () -> Job,
    homeUIState: HomeUIState,
    homeSheetUIState: HomeSheetUIState,
    favoriteMovies: List<Movie>,
    searchViewModel: SearchViewModel
) {
    val tabPage = rememberSaveable { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize()
    ) {

        HomeTabsContainer(tabPage)

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            searchViewModel.uiState.actorList.clear()
            when (tabPage.value) {
                0 -> {
                    ActorsTabContent(
                        homeUIState = homeUIState,
                        getSelectedActorDetails = selectedActor
                    )
                    searchViewModel.searchType = SearchType.Actors
                }
                1 -> {
                    MoviesTabContent(
                        homeUIState = homeUIState,
                        getSelectedMovieDetails = selectedMovie,
                        openHomeBottomSheet = openHomeBottomSheet
                    )
                    searchViewModel.searchType = SearchType.Movies
                }
                2 -> FavoritesTabContent(
                    homeUIState = homeUIState,
                    getSelectedMovieDetails = selectedMovie,
                    openHomeBottomSheet = openHomeBottomSheet,
                    favoriteMovies = favoriteMovies
                )
            }
        }

        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}