package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SheetHorizontalSeparator(
    modifier: Modifier = Modifier
) {
    Divider(
        color = MaterialTheme.colors.onBackground.copy(0.50f),
        thickness = 4.dp,
        modifier = modifier
            .clip(RoundedCornerShape(percent = 100))
            .width(48.dp)
    )
}