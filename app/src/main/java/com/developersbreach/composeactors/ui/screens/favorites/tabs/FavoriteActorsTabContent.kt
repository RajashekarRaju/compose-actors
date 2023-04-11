package com.developersbreach.composeactors.ui.screens.favorites.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.ui.screens.favorites.NoFavoritesFoundUI
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@Composable
fun FavoriteActorsTabContent(
    getSelectedActorDetails: (Int) -> Unit,
    favoriteActors: List<Actor>
) {
    if (favoriteActors.isEmpty()) {
        NoFavoritesFoundUI()
    }
}

@Preview
@Composable
private fun FavoriteActorsTabContentPreview() {
    ComposeActorsTheme {
        FavoriteActorsTabContent(
            getSelectedActorDetails = {},
            favoriteActors = emptyList()
        )
    }
}