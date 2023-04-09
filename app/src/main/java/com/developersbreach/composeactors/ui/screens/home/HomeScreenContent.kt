package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.developersbreach.composeactors.ui.screens.search.SearchType
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
    updateSearchType: (SearchType) -> Unit
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
            when (tabPage.value) {
                0 -> {
                    updateSearchType(SearchType.Actors)
                    ActorsTabContent(
                        homeUIState = homeUIState,
                        getSelectedActorDetails = selectedActor
                    )
                }
                1 -> {
                    updateSearchType(SearchType.Movies)
                    MoviesTabContent(
                        homeUIState = homeUIState,
                        getSelectedMovieDetails = selectedMovie,
                        openHomeBottomSheet = openHomeBottomSheet
                    )
                }
                2 -> {
                    updateSearchType(SearchType.Movies)
                    FavoritesTabContent(
                        getSelectedMovieDetails = selectedMovie,
                        openHomeBottomSheet = openHomeBottomSheet,
                        favoriteMovies = favoriteMovies
                    )
                }
            }
        }
    }
}