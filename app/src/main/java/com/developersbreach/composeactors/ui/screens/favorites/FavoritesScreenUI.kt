package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.components.AppDivider
import com.developersbreach.composeactors.ui.components.TabItem
import com.developersbreach.composeactors.ui.components.TabsContainer
import com.developersbreach.composeactors.ui.screens.favorites.tabs.FavoriteActorsTabContent
import com.developersbreach.composeactors.ui.screens.favorites.tabs.FavoriteMoviesTabContent
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreenUI(
    navigateUp: () -> Unit,
    favoriteMovies: List<Movie>,
    navigateToSelectedMovie: (Int) -> Unit,
    removeFavoriteMovie: (Movie) -> Unit,
    navigateToSelectedActor: (Int) -> Unit,
    favoriteActors: List<FavoriteActor>,
    removeFavoriteActor: (FavoriteActor) -> Unit,
) {
    val favoriteTabs = listOf(
        TabItem("Actors"),
        TabItem("Movies")
    )
    val favoritesPagerState = rememberPagerState(
        pageCount = { favoriteTabs.size }
    )

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        Scaffold(
            topBar = {
                FavoritesTopAppBar(navigateUp = navigateUp)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                TabsContainer(tabs = favoriteTabs, pagerState = favoritesPagerState)
                AppDivider(thickness = 1.dp, verticalPadding = 0.dp)
                HorizontalPager(
                    state = favoritesPagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { index ->
                    when (index) {
                        0 -> FavoriteActorsTabContent(
                            navigateToSelectedActor = navigateToSelectedActor,
                            favoriteActors = favoriteActors,
                            removeFavoriteActor = removeFavoriteActor
                        )

                        1 -> FavoriteMoviesTabContent(
                            navigateToSelectedMovie = navigateToSelectedMovie,
                            favoriteMovies = favoriteMovies,
                            removeFavoriteMovie = removeFavoriteMovie
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
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Feature Coming Soon",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 40.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun FavoriteScreenUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        FavoritesScreenUI(
            favoriteMovies = emptyList(),
            navigateToSelectedMovie = {},
            removeFavoriteMovie = {},
            navigateToSelectedActor = {},
            favoriteActors = emptyList(),
            removeFavoriteActor = {},
            navigateUp = {}
        )
    }
}

@Preview
@Composable
private fun FavoriteScreenUIDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        FavoritesScreenUI(
            favoriteMovies = emptyList(),
            navigateToSelectedMovie = {},
            removeFavoriteMovie = {},
            navigateToSelectedActor = {},
            favoriteActors = emptyList(),
            removeFavoriteActor = {},
            navigateUp = {}
        )
    }
}