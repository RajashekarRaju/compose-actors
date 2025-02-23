package com.developersbreach.designsystem.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CaIcon(
    modifier: Modifier,
    imageVector: ImageVector,
    contentDescription: String?,
    tint: Color = MaterialTheme.colors.onBackground
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        tint = tint,
        contentDescription = contentDescription,
    )
}

@Composable
fun CaIcon(
    modifier: Modifier,
    painter: Painter,
    contentDescription: String?,
    tint: Color = MaterialTheme.colors.onBackground
) {
    Icon(
        modifier = modifier,
        tint = tint,
        contentDescription = contentDescription,
        painter = painter
    )
}