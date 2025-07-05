package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
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
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaSurface
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreenUI(
    modifier: Modifier = Modifier,
    navigateToWatchlist: () -> Unit,
    navigateToSearch: (SearchType) -> Unit,
    navigateToAbout: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSelectedPerson: (Int) -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    data: HomeUiState,
    scaffoldState: ScaffoldState,
    updateHomeSearchType: (SearchType) -> Unit,
) {
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = tween(durationMillis = 500),
        skipHalfExpanded = true,
    )

    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = modifier,
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
                    navigateToWatchlist = navigateToWatchlist,
                    navigateToSearch = { navigateToSearch(SearchType.Movies) },
                    navigateToProfile = navigateToProfile,
                    navigateToAbout = navigateToAbout,
                )
            },
        ) {
            CaScaffold(
                modifier = Modifier,
                scaffoldState = scaffoldState,
                topBar = {
                    HomeTopAppBar(
                        modifier = Modifier.testTag("TestTag:HomeTopAppBar"),
                        navigateToSearch = navigateToSearch,
                        searchType = data.searchType,
                    )
                },
                bottomBar = {
                    HomeBottomBar(
                        modalSheetSheet = modalSheetState,
                    )
                },
                snackbarHost = { HomeSnackbar(it) },
                content = {
                    Box(
                        modifier = modifier.padding(paddingValues = it),
                    ) {
                        // Main content for this screen
                        HomeScreenUI(
                            navigateToSelectedPerson = navigateToSelectedPerson,
                            data = data,
                            navigateToSelectedMovie = navigateToSelectedMovie,
                            updateSearchType = updateHomeSearchType,
                        )

                        // Perform network check and show snackbar if offline
                        IfOfflineShowSnackbar(scaffoldState)

                        // If Api key is missing, show a SnackBar.
                        ApiKeyMissingShowSnackbar(scaffoldState)
                    }
                },
            )
        }
    }
}

@Composable
private fun HomeScreenUI(
    navigateToSelectedPerson: (Int) -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    data: HomeUiState,
    updateSearchType: (navigateToSearchByType: SearchType) -> Unit,
) {
    val popularPersonsListState = rememberLazyListState()
    val trendingPersonsListState = rememberLazyListState()
    val homeTabs = listOf(
        TabItem(stringResource(R.string.actors)),
        TabItem(stringResource(R.string.movies)),
        TabItem(stringResource(R.string.tv_shows)),
    )
    val homePagerState = rememberPagerState(
        pageCount = { homeTabs.size },
    )

    Column(
        Modifier.fillMaxSize(),
    ) {
        TabsContainer(tabs = homeTabs, pagerState = homePagerState)
        CaDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 0.dp),
            colorAlpha = 0.1f,
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        HorizontalPager(
            state = homePagerState,
        ) {
            when (it) {
                0 -> {
                    updateSearchType(SearchType.People)
                    PersonsTabContent(
                        data = data,
                        navigateToSelectedPerson = navigateToSelectedPerson,
                        popularPersonsListState = popularPersonsListState,
                        trendingPersonsListState = trendingPersonsListState,
                    )
                }

                1 -> {
                    updateSearchType(SearchType.Movies)
                    MoviesTabContent(
                        data = data,
                        navigateToSelectedMovie = navigateToSelectedMovie,
                    )
                }

                2 -> {
                    TvShowsTabContent()
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun HomeScreenUIPreview() {
    ComposeActorsTheme {
        HomeScreenUI(
            navigateToSelectedPerson = {},
            navigateToSelectedMovie = {},
            updateSearchType = {},
            data = HomeUiState(
                popularPersonList = fakePersonsList(),
                trendingPersonList = fakePersonsList(),
                isFetchingPersons = false,
                upcomingMoviesList = fakeMovieList(),
                nowPlayingMoviesList = flow { fakeMovieList() },
            ),
        )
    }
}