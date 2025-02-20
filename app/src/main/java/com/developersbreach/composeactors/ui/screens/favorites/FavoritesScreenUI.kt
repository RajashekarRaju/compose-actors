package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.person.model.FavoritePerson
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.ui.components.TabItem
import com.developersbreach.composeactors.ui.components.TabsContainer
import com.developersbreach.composeactors.ui.screens.favorites.tabs.FavoritePersonsTabContent
import com.developersbreach.composeactors.ui.screens.favorites.tabs.FavoriteMoviesTabContent
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaDivider
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaSurface

@Composable
fun FavoritesScreenUI(
    navigateUp: () -> Unit,
    favoriteMovies: List<Movie>,
    navigateToSelectedMovie: (Int) -> Unit,
    removeFavoriteMovie: (Movie) -> Unit,
    navigateToSelectedPerson: (Int) -> Unit,
    favoritePeople: List<FavoritePerson>,
    removeFavoritePerson: (FavoritePerson) -> Unit,
) {
    val favoriteTabs = listOf(
        TabItem("Actors"),
        TabItem("Movies")
    )
    val favoritesPagerState = rememberPagerState(
        pageCount = { favoriteTabs.size }
    )

    CaSurface(
        color = MaterialTheme.colors.background,
        content = {
            CaScaffold(
                modifier = Modifier,
                topBar = {
                    FavoritesTopAppBar(navigateUp = navigateUp)
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = paddingValues)
                    ) {
                        TabsContainer(tabs = favoriteTabs, pagerState = favoritesPagerState)
                        CaDivider(
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 0.dp),
                            colorAlpha = 0.1f
                        )
                        HorizontalPager(
                            state = favoritesPagerState,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) { index ->
                            when (index) {
                                0 -> FavoritePersonsTabContent(
                                    navigateToSelectedPerson = navigateToSelectedPerson,
                                    favoritePeople = favoritePeople,
                                    removeFavoritePerson = removeFavoritePerson
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
            )
        }
    )
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
fun FavoriteScreenUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        FavoritesScreenUI(
            favoriteMovies = emptyList(),
            navigateToSelectedMovie = {},
            removeFavoriteMovie = {},
            navigateToSelectedPerson = {},
            favoritePeople = emptyList(),
            removeFavoritePerson = {},
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
            navigateToSelectedPerson = {},
            favoritePeople = emptyList(),
            removeFavoritePerson = {},
            navigateUp = {}
        )
    }
}