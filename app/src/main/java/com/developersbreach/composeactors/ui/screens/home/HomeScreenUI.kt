package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.components.AppDivider
import com.developersbreach.composeactors.ui.components.TabItem
import com.developersbreach.composeactors.ui.components.TabsContainer
import com.developersbreach.composeactors.ui.screens.home.tabs.ActorsTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.MoviesTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.TvShowsTabContent
import com.developersbreach.composeactors.ui.screens.search.SearchType
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenUI(
    selectedActor: (Int) -> Unit,
    selectedMovie: (Int) -> Unit,
    homeUIState: HomeUIState,
    homeSheetUIState: HomeSheetUIState,
    favoriteMovies: List<Movie>,
    updateSearchType: (SearchType) -> Unit
) {
    val popularActorsListState = rememberLazyListState()
    val trendingActorsListState = rememberLazyListState()
    val homePagerState = rememberPagerState()
    val homeTabs = listOf(
        TabItem("Actors"),
        TabItem("Movies"),
        TabItem("TV Shows")
    )

    Column(
        Modifier.fillMaxSize()
    ) {
        TabsContainer(tabs = homeTabs, pagerState = homePagerState)
        AppDivider(thickness = 1.dp, verticalPadding = 0.dp)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        HorizontalPager(
            state = homePagerState,
            pageCount = homeTabs.size
        ) {
            when (it) {
                0 -> {
                    updateSearchType(SearchType.Actors)
                    ActorsTabContent(
                        homeUIState = homeUIState,
                        getSelectedActorDetails = selectedActor,
                        popularActorsListState = popularActorsListState,
                        trendingActorsListState = trendingActorsListState
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