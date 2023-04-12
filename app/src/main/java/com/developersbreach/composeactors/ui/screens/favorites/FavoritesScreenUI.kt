package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.components.TabItem
import com.developersbreach.composeactors.ui.components.TabsContainer
import com.developersbreach.composeactors.ui.screens.favorites.tabs.FavoriteMoviesTabContent
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@Composable
fun FavoritesScreenUI(
    favoriteMovies: List<Movie>,
    selectedMovie: (Int) -> Unit,
    removeFavoriteMovie: (Movie) -> Unit
) {
    val tabPage = rememberSaveable { mutableStateOf(0) }
    val favoriteTabs = listOf(
        TabItem("Actors"),
        TabItem("Movies")
    )

    Column(
        Modifier.fillMaxSize()
    ) {

        TabsContainer(favoriteTabs, tabPage)

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (tabPage.value) {
                0 -> FeatureComingSoonTextUI()
                1 -> FavoriteMoviesTabContent(
                    getSelectedMovieDetails = selectedMovie,
                    favoriteMovies = favoriteMovies,
                    removeFavoriteMovie = removeFavoriteMovie
                )
                2 -> FeatureComingSoonTextUI()
            }
        }
    }
}

@Composable
fun FeatureComingSoonTextUI() {
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
private fun FavoriteScreenUIPreview() {
    ComposeActorsTheme {
        FavoritesScreenUI(
            favoriteMovies = emptyList(),
            selectedMovie = {},
            removeFavoriteMovie = {}
        )
    }
}