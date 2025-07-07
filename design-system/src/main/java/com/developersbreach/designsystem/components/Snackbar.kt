package com.developersbreach.designsystem.components

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CaSnackbar(
    modifier: Modifier,
    hostState: SnackbarHostState
) {
    SnackbarHost(
        hostState = hostState
    ) { data ->
        Snackbar(
            modifier = modifier,
            snackbarData = data
        )
    }
}