package com.developersbreach.composeactors.ui.screens.watchlist.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakeWatchlistPersonsList
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import com.developersbreach.composeactors.ui.components.ImageBackgroundThemeGenerator
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.verticalGradientScrim
import com.developersbreach.composeactors.ui.screens.watchlist.NoWatchlistFoundUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.utils.getPlaceOfBirth
import com.developersbreach.designsystem.components.CaIconButton
import com.developersbreach.designsystem.components.CaTextH5
import com.developersbreach.designsystem.components.CaTextSubtitle1
import kotlinx.coroutines.flow.flowOf

@Composable
fun WatchlistPersonsTabContent(
    navigateToSelectedPerson: (Int) -> Unit,
    watchlistPeople: LazyPagingItems<WatchlistPerson>,
    removePersonFromWatchlist: (WatchlistPerson) -> Unit,
) {
    if (watchlistPeople.itemSnapshotList.isEmpty()) {
        NoWatchlistFoundUI()
    }

    val listState = rememberPagerState(
        pageCount = { watchlistPeople.itemCount },
    )

    VerticalPager(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        pageSpacing = 24.dp,
        pageSize = PageSize.Fixed(512.dp),
        contentPadding = PaddingValues(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 48.dp),
    ) { currentPage ->
        watchlistPeople[currentPage]?.let {
            ItemWatchlistPerson(
                item = it,
                onClickPerson = navigateToSelectedPerson,
                removePersonFromWatchlist = removePersonFromWatchlist,
            )
        }
    }
}

@Composable
private fun ItemWatchlistPerson(
    item: WatchlistPerson,
    onClickPerson: (Int) -> Unit,
    removePersonFromWatchlist: (WatchlistPerson) -> Unit,
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.large),
    ) {
        LoadNetworkImage(
            imageUrl = item.profileUrl,
            contentDescription = "Actor Image",
            shape = RectangleShape,
            showAnimProgress = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(512.dp)
                .clickable {
                    onClickPerson(item.personId)
                },
        )

        ImageBackgroundThemeGenerator(
            imageUrl = item.profileUrl,
            backgroundColor = MaterialTheme.colors.primary,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(124.dp)
                    .verticalGradientScrim(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.75f),
                        startYPercentage = 0f,
                        endYPercentage = 1f,
                    ),
            )
        }

        ImageBackgroundThemeGenerator(
            imageUrl = item.profileUrl,
            backgroundColor = MaterialTheme.colors.onSurface,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 20.dp, end = 12.dp),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    CaTextH5(
                        text = item.personName,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )

                    getPlaceOfBirth(item.placeOfBirth)?.let {
                        CaTextSubtitle1(
                            text = it,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier,
                        )
                    }
                }

                CaIconButton(
                    iconModifier = Modifier,
                    onClick = { removePersonFromWatchlist(item) },
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.ic_added_to_watchlist),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun WatchlistPersonsTabContentPreview() {
    ComposeActorsTheme {
        WatchlistPersonsTabContent(
            navigateToSelectedPerson = {},
            watchlistPeople = flowOf(PagingData.from(fakeWatchlistPersonsList())).collectAsLazyPagingItems(),
            removePersonFromWatchlist = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun WatchlistPersonsTabContentNoWatchlistPreview() {
    ComposeActorsTheme {
        WatchlistPersonsTabContent(
            navigateToSelectedPerson = {},
            watchlistPeople = flowOf(PagingData.from(fakeWatchlistPersonsList())).collectAsLazyPagingItems(),
            removePersonFromWatchlist = {},
        )
    }
}