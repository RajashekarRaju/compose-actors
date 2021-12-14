package com.developersbreach.composeactors.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = primary,
    onPrimary = onPrimary,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface
)

private val LightColorPalette = lightColors(
    primary = primary,
    onPrimary = onPrimary,
    background = background,
    onBackground = onBackground,
    surface = surface,
    onSurface = onSurface
)

@Composable
fun ComposeActorsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}