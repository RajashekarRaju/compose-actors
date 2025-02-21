package com.developersbreach.designsystem.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CaSurface(
    modifier: Modifier,
    content: @Composable () -> Unit,
    color: Color = MaterialTheme.colors.surface,
    ) {
    Surface(
        modifier =modifier,
        content = content
    )
}