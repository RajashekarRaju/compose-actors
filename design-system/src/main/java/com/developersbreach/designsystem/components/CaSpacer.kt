package com.developersbreach.designsystem.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CaVerticalSpacer(
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier)
}

@Composable
fun CaVerticalSpacer(
    value: Int = 8,
) {
    Spacer(modifier = Modifier.padding(vertical = value.dp))
}