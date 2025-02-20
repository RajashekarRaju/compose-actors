package com.developersbreach.designsystem.components

import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CaDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onBackground,
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp,
    colorAlpha: Float = 0.50f,
) {
    Divider(
        color = color.copy(alpha = colorAlpha),
        thickness = thickness,
        startIndent = startIndent,
        modifier = modifier
    )
}