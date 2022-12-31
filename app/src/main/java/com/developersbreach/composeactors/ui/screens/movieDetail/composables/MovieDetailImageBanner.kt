package com.developersbreach.composeactors.ui.screens.movieDetail.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.LoadNetworkImage

@Composable
fun MovieDetailImageBanner(
    bannerUrl: String?
) {
    LoadNetworkImage(
        imageUrl = bannerUrl.toString(),
        contentDescription = "",
        shape = MaterialTheme.shapes.large,
        showAnimProgress = false,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .aspectRatio(1.8f)
            .shadow(elevation = 8.dp),
    )
}