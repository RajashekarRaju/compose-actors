package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.model.WatchlistPerson
import com.developersbreach.composeactors.ui.components.TabItem
import com.developersbreach.composeactors.ui.components.TabsContainer
import com.developersbreach.composeactors.ui.components.UiEvent
import com.developersbreach.composeactors.ui.screens.watchlist.tabs.WatchlistMoviesTabContent
import com.developersbreach.composeactors.ui.screens.watchlist.tabs.WatchlistPersonsTabContent
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaDivider
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaSurface
import com.developersbreach.designsystem.components.CaTextH6
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun WatchlistScreenUI(
    navigateUp: () -> Unit,
    watchlistMovies: LazyPagingItems<Movie>,
    navigateToSelectedMovie: (Int) -> Unit,
    removeMovieFromWatchlist: (Movie) -> Unit,
    navigateToSelectedPerson: (Int) -> Unit,
    watchlistPersons: List<WatchlistPerson>,
    removeWatchlistPerson: (WatchlistPerson) -> Unit,
    uiEvent: SharedFlow<UiEvent>,
) {
    val watchlistTabs = listOf(
        TabItem(stringResource(R.string.actors)),
        TabItem(stringResource(R.string.movies)),
    )
    val watchlistPagerState = rememberPagerState(
        pageCount = { watchlistTabs.size },
    )

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(Unit) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowMessage -> scaffoldState.snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = Modifier,
    ) {
        CaScaffold(
            modifier = Modifier,
            scaffoldState = scaffoldState,
            topBar = {
                WatchlistTopAppBar(navigateUp = navigateUp)
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
            ) {
                TabsContainer(tabs = watchlistTabs, pagerState = watchlistPagerState)
                CaDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 0.dp),
                    colorAlpha = 0.1f,
                )
                HorizontalPager(
                    state = watchlistPagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) { index ->
                    when (index) {
                        0 -> WatchlistPersonsTabContent(
                            navigateToSelectedPerson = navigateToSelectedPerson,
                            watchlistPeople = watchlistPersons,
                            removePersonFromWatchlist = removeWatchlistPerson,
                        )

                        1 -> WatchlistMoviesTabContent(
                            navigateToSelectedMovie = navigateToSelectedMovie,
                            watchlistMovies = watchlistMovies,
                            removeMovieFromWatchlist = removeMovieFromWatchlist,
                        )

                        2 -> FeatureComingSoonTextUI()
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureComingSoonTextUI() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        CaTextH6(
            text = "Feature Coming Soon",
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 40.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@PreviewLightDark
@Composable
fun WatchlistScreenUIPreview() {
    ComposeActorsTheme {
        WatchlistScreenUI(
            navigateUp = {},
            watchlistMovies = flowOf(PagingData.from(fakeMovieList())).collectAsLazyPagingItems(),
            navigateToSelectedMovie = {},
            removeMovieFromWatchlist = {},
            navigateToSelectedPerson = {},
            watchlistPersons = emptyList(),
            removeWatchlistPerson = {},
            uiEvent = MutableSharedFlow(),
        )
    }
}