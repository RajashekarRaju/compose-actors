package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@Composable
fun FavoritesTopBar() {
    TopAppBar(
        content = { FavoritesTopBarContent() },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier
            .statusBarsPadding()
            .padding(bottom = 16.dp)
    )
}

@Composable
private fun FavoritesTopBarContent() {
    Column( modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 48.dp)
        .background(color = MaterialTheme.colors.background)
        .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Favorites", color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.h6)
    }
}

@Preview
@Composable
private fun FavoritesTopBarPreview() {
    ComposeActorsTheme {
        FavoritesTopBar()
    }
}
