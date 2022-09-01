package com.developersbreach.composeactors.ui.screens.actorDetails.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.LoadNetworkImage

/**
 * Load circular network image of actor at the top of screen.
 */
@Composable
internal fun ActorRoundProfile(
    profileUrl: String,
    size: Dp = 120.dp
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LoadNetworkImage(
            imageUrl = profileUrl,
            contentDescription = stringResource(id = R.string.cd_actor_image),
            shape = CircleShape,
            modifier = Modifier
                .size(size)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape
                )
        )
    }
}