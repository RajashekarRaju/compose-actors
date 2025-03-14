package com.developersbreach.composeactors.ui.screens.actorDetails.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.LoadNetworkImage
import com.developersbreach.composeactors.ui.components.verticalGradientScrim

/**
 * This image takes up whole space in screen as a background with reduced opacity.
 * On foreground draw vertical gradient so that top elements will be visible.
 *
 * @param imageUrl url to load image with coil.
 */
@Composable
internal fun ActorBackgroundWithGradientForeground(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box {
        LoadNetworkImage(
            imageUrl = imageUrl,
            contentDescription = stringResource(R.string.cd_actor_banner),
            shape = RectangleShape,
            showAnimProgress = false,
            modifier = modifier
                .fillMaxSize()
                .alpha(0.5f),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalGradientScrim(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                    startYPercentage = 1f,
                    endYPercentage = 0f,
                ),
        )
    }
}