package com.developersbreach.composeactors.ui.screens.actorDetails.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ActorInfoHeaderSubtitle(
    subtitle: String
) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colors.onBackground
    )
}