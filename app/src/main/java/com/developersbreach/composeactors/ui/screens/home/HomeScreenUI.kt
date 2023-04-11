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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.components.TabItem
import com.developersbreach.composeactors.ui.components.TabsContainer
import com.developersbreach.composeactors.ui.screens.home.tabs.ActorsTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.MoviesTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.TvShowsTabContent
import com.developersbreach.composeactors.ui.screens.search.SearchType
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreenUI(
    selectedActor: (Int) -> Unit,
    selectedMovie: (Int) -> Unit,
    homeUIState: HomeUIState,
    homeSheetUIState: HomeSheetUIState,
    favoriteMovies: List<Movie>,
    updateSearchType: (SearchType) -> Unit
) {
    val tabPage = rememberSaveable { mutableStateOf(0) }
    val homeTabs = listOf(
        TabItem("Actors"),
        TabItem("Movies"),
        TabItem("TV Shows")
    )

    Column(
        Modifier.fillMaxSize()
    ) {

        TabsContainer(homeTabs, tabPage)

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
                        homeSheetUIState = homeSheetUIState,
                        getSelectedMovieDetails = selectedMovie
                    )
                }
                2 -> {
                    TvShowsTabContent(
                        homeSheetUIState = homeSheetUIState
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenUIPreview() {
    ComposeActorsTheme {
        HomeScreenUI(
            selectedActor = {},
            selectedMovie = {},
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
            favoriteMovies = emptyList(),
            updateSearchType = {}
        )
    }
}