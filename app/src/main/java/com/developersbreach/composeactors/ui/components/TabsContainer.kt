package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalRippleConfiguration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.designsystem.components.CaTextSubtitle2
import kotlinx.coroutines.launch

@Composable
fun TabsContainer(
    modifier: Modifier = Modifier,
    tabs: List<TabItem>,
    pagerState: PagerState,
) {
    val coroutineScope = rememberCoroutineScope()

    CompositionLocalProvider(
        LocalRippleConfiguration provides null,
    ) {
        TabRow(
            modifier = modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background,
            selectedTabIndex = pagerState.currentPage,
            divider = { },
            indicator = { tabPositions ->
                RoundedTabIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                )
            },
        ) {
            tabs.forEachIndexed { tabIndex, currentTab ->
                Tab(
                    selected = pagerState.currentPage == tabIndex,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.primary.copy(0.50f),
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(tabIndex) } },
                    text = {
                        CaTextSubtitle2(
                            text = currentTab.tabName,
                            modifier = Modifier,
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun RoundedTabIndicator(
    modifier: Modifier,
) {
    Spacer(
        modifier
            .padding(horizontal = 40.dp)
            .height(2.dp)
            .background(
                MaterialTheme.colors.primary,
                RoundedCornerShape(percent = 100),
            ),
    )
}

@Immutable
data class TabItem(
    val tabName: String,
)