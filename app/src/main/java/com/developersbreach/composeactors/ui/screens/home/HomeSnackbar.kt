package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaSnackbar

@Composable
fun HomeSnackbar(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    CaSnackbar(
        hostState = hostState,
        modifier = modifier.padding(bottom = 48.dp)
    )
}

@Preview
@Composable
private fun HomeSnackbarPreview() {
    ComposeActorsTheme {
        HomeSnackbar(hostState = SnackbarHostState())
    }
}