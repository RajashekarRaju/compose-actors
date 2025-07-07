package com.developersbreach.designsystem.components

import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape


@Composable
fun CaCard(
    modifier: Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        content = content,
        shape = shape,
        backgroundColor = backgroundColor
    )
}