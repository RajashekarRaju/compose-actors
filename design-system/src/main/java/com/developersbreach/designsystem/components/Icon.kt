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
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String?,
    iconModifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onBackground
) {
    Icon(
        modifier = iconModifier,
        imageVector = imageVector,
        tint = tint,
        contentDescription = contentDescription,
    )
}

@Composable
fun CaIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String?,
    iconModifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onBackground
) {
    Icon(
        modifier = iconModifier,
        tint = tint,
        contentDescription = contentDescription,
        painter = painter
    )
}