package com.developersbreach.designsystem.components

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CaSnackbar(
    modifier: Modifier,
    snackbarData: SnackbarData
) {
    Snackbar(
        modifier = modifier,
        snackbarData = snackbarData
    )
}