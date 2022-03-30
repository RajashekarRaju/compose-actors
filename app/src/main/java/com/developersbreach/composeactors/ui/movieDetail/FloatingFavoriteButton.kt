package com.developersbreach.composeactors.ui.movieDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R

/**
 * TODO implement add movie to favorites feature.
 *
 * @param showSnackBarMessage
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FloatingDialPadButton(
    showSnackBarMessage: () -> Unit,
) {
    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        AnimatedVisibility(
            visibleState = state,
            enter = scaleIn(animationSpec = tween(250, 0, LinearEasing)),
            exit = scaleOut(animationSpec = tween(250, 0, LinearEasing))
        ) {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                onClick = { showSnackBarMessage() },
                modifier = Modifier
                    .navigationBarsPadding()
                    .align(Alignment.BottomEnd),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}