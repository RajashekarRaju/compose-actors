package com.developersbreach.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale


@Composable
fun CaImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription =  contentDescription,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter
    )
}