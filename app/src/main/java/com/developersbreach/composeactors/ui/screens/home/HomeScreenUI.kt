package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieDetail
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.ui.components.ApiKeyMissingShowSnackbar
import com.developersbreach.composeactors.ui.components.IfOfflineShowSnackbar
import com.developersbreach.composeactors.ui.components.TabItem
import com.developersbreach.composeactors.ui.components.TabsContainer
import com.developersbreach.composeactors.ui.screens.home.tabs.PersonsTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.MoviesTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.TvShowsTabContent
import com.developersbreach.composeactors.ui.screens.modalSheets.OptionsModalSheetContent
import com.developersbreach.composeactors.ui.screens.search.SearchType
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaDivider
import kotlinx.coroutines.flow.flow

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navigateToFavorite: () -> Unit,
    navigateToSearch: (SearchType) -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSearchBySearchType: SearchType,
    navigateToSelectedPerson: (Int) -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    data: HomeData,
    sheetUiState: HomeSheetUIState,
    updateHomeSearchType: (SearchType) -> Unit
) {
    // Remember state of scaffold to manage snackbar
    val scaffoldState = rememberScaffoldState()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = tween(durationMillis = 500),
        skipHalfExpanded = true
    )

    Surface(
        color = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        // TODO Replace ModalSheet with BottomSheetScaffold
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = MaterialTheme.colors.background,
            sheetContent = {
                OptionsModalSheetContent(
                    modalSheetSheet = modalSheetState,
                    navigateToFavorite = navigateToFavorite,
                    navigateToSearch = { navigateToSearch(SearchType.Movies) },
                    navigateToProfile = { },
                    navigateToAbout = navigateToAbout
                )
            },
        ) {
            Scaffold(
                // attach snackbar host state to the scaffold
                scaffoldState = scaffoldState,
                // Custom AppBar contains fake search bar.
                topBar = {
                    HomeTopAppBar(
                        modifier = Modifier.testTag("TestTag:HomeTopAppBar"),
                        navigateToSearch = navigateToSearch,
                        searchType = navigateToSearchBySearchType
                    )
                },
                bottomBar = {
                    HomeBottomBar(
                        modalSheetSheet = modalSheetState
                    )
                },
                // Host for custom snackbar
                snackbarHost = { HomeSnackbar(it) }
            ) { paddingValues ->
                Box(
                    modifier = modifier.padding(paddingValues = paddingValues)
                ) {
                    // Main content for this screen
                    HomeScreenUI(
                        navigateToSelectedPerson = navigateToSelectedPerson,
                        data = data,
                        homeSheetUIState = sheetUiState,
                        navigateToSelectedMovie = navigateToSelectedMovie,
                        updateSearchType = updateHomeSearchType
                    )

                    // Perform network check and show snackbar if offline
                    IfOfflineShowSnackbar(scaffoldState)

                    // If Api key is missing, show a SnackBar.
                    ApiKeyMissingShowSnackbar(scaffoldState)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenUI(
    navigateToSelectedPerson: (Int) -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    data: HomeData,
    homeSheetUIState: HomeSheetUIState,
    updateSearchType: (navigateToSearchByType: SearchType) -> Unit
) {
    val popularPersonsListState = rememberLazyListState()
    val trendingPersonsListState = rememberLazyListState()
    val homeTabs = listOf(
        TabItem("Actors"),
        TabItem("Movies"),
        TabItem("TV Shows")
    )
    val homePagerState = rememberPagerState(
        pageCount = { homeTabs.size }
    )

    Column(
        Modifier.fillMaxSize()
    ) {
        TabsContainer(tabs = homeTabs, pagerState = homePagerState)
        CaDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 0.dp),
            colorAlpha = 0.1f
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        HorizontalPager(
            state = homePagerState,
        ) {
            when (it) {
                0 -> {
                    updateSearchType(SearchType.Persons)
                    PersonsTabContent(
                        data = data,
                        navigateToSelectedPerson = navigateToSelectedPerson,
                        popularPersonsListState = popularPersonsListState,
                        trendingPersonsListState = trendingPersonsListState
                    )
                }

                1 -> {
                    updateSearchType(SearchType.Movies)
                    MoviesTabContent(
                        data = data,
                        homeSheetUIState = homeSheetUIState,
                        navigateToSelectedMovie = navigateToSelectedMovie
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

@Preview(showBackground = true)
@Composable
fun HomeScreenUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        HomeScreenUI(
            navigateToSelectedPerson = {},
            navigateToSelectedMovie = {},
            homeSheetUIState = HomeSheetUIState(fakeMovieDetail),
            updateSearchType = {},
            data = HomeData(
                popularPersonList = fakePersonsList(),
                trendingPersonList = fakePersonsList(),
                isFetchingPersons = false,
                upcomingMoviesList = fakeMovieList(),
                nowPlayingMoviesList = flow { fakeMovieList() }
            ),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun HomeScreenUIDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        HomeScreenUI(
            navigateToSelectedPerson = {},
            navigateToSelectedMovie = {},
            homeSheetUIState = HomeSheetUIState(fakeMovieDetail),
            updateSearchType = {},
            data = HomeData(
                popularPersonList = fakePersonsList(),
                trendingPersonList = fakePersonsList(),
                isFetchingPersons = false,
                upcomingMoviesList = fakeMovieList(),
                nowPlayingMoviesList = flow { fakeMovieList() }
            ),
        )
    }
}