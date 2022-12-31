package com.developersbreach.composeactors.ui.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.TransparentRippleTheme

@Composable
fun HomeTabsContainer(
    tabPage: MutableState<Int>
) {
    CompositionLocalProvider(
        LocalRippleTheme provides TransparentRippleTheme
    ) {
        TabRow(
            backgroundColor = MaterialTheme.colors.background,
            selectedTabIndex = tabPage.value,
            indicator = { tabPositions ->
                RoundedTabIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabPage.value])
                )
            }
        ) {
            tabs.forEachIndexed { tabIndex, currentTab ->
                Tab(
                    selected = tabPage.value == tabIndex,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.primary.copy(0.50f),
                    onClick = { tabPage.value = tabIndex },
                    text = {
                        Text(
                            text = currentTab.tabName,
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun RoundedTabIndicator(
    modifier: Modifier
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(2.dp)
            .background(
                MaterialTheme.colors.primary,
                RoundedCornerShape(percent = 100)
            )
    )
}

@Immutable
private data class Tabs(
    val tabName: String
)

private val tabs = listOf(
    Tabs("Actors"),
    Tabs("Movies"),
    Tabs("Favorites")
)