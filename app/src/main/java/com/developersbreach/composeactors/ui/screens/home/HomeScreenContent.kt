package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.TransparentRippleTheme
import com.developersbreach.composeactors.ui.screens.home.tabs.ActorsTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.FavoritesTabContent
import com.developersbreach.composeactors.ui.screens.home.tabs.MoviesTabContent
import kotlinx.coroutines.Job


// Main content for the home screen.
@Composable
fun HomeScreenContent(
    selectedActor: (Int) -> Unit,
    viewModel: HomeViewModel,
    selectedMovie: (Int) -> Unit,
    openHomeBottomSheet: () -> Job
) {
    val tabPage = rememberSaveable { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize()
    ) {

        TabsContainer(tabPage)

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (tabPage.value) {
                0 -> ActorsTabContent(viewModel, selectedActor)
                1 -> MoviesTabContent(viewModel, selectedMovie, openHomeBottomSheet)
                2 -> FavoritesTabContent(viewModel, selectedMovie, openHomeBottomSheet)
            }
        }

        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}

@Composable
private fun TabsContainer(
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