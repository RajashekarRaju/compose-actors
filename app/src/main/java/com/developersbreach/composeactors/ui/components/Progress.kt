package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.animations.AnimatedSearch
import com.developersbreach.composeactors.ui.animations.InfinitelyFlowingCircles
import com.developersbreach.composeactors.utils.isTmdbApiKeyNotValid

/**
 * @param isLoadingData if true circular progress bar will show.
 * Same composable used in all screens.
 */
@Composable
fun ShowProgressIndicator(
    isLoadingData: Boolean,
) {
    if (isLoadingData && isTmdbApiKeyNotValid()) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.onBackground,
            )
        }
    }
}

/**
 * @param isLoadingData does not show a progress bar, instead draws circle on canvas.
 * On screen opened infinite animation applied on search and circle shapes.
 */
@Composable
fun ShowSearchProgress(
    isLoadingData: Boolean,
) {
    if (isLoadingData) {
        InfinitelyFlowingCircles()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 28.dp, end = 28.dp),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedSearch()
        }
    }
}