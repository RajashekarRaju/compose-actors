package com.developersbreach.composeactors.ui.screens.actorDetails.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.animations.borderRevealAnimation
import com.developersbreach.composeactors.utils.calculateAge
import com.developersbreach.composeactors.utils.getPopularity

/**
 * Third element in [ActorInfoHeader].
 * Fetches actors popularity from function [calculateAge].
 */
@Composable
internal fun PopularityInfo(
    popularity: Double?
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.borderRevealAnimation()
        ) {
            Text(
                text = getPopularity(popularity),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(alignment = Alignment.Center)
            )
        }

        ActorInfoHeaderSubtitle(
            subtitle = stringResource(R.string.actor_popularity_subtitle)
        )
    }
}