package com.developersbreach.designsystem.components

import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CaIconButton(
    modifier: Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String?,
    iconModifier: Modifier,
    tint: Color = MaterialTheme.colors.onBackground
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        content = {
            CaIcon(
                modifier= iconModifier,
                imageVector = imageVector,
                tint = tint,
                contentDescription = contentDescription,
            )
        }
    )
}

@Composable
fun CaIconButton(
    modifier: Modifier,
    onClick: () -> Unit,
    painter: Painter,
    contentDescription: String?,
    iconModifier: Modifier,
    tint: Color = MaterialTheme.colors.onBackground
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        content = {
            CaIcon(
                modifier = iconModifier,
                tint = tint,
                contentDescription = contentDescription,
                painter = painter
            )
        }
    )
}