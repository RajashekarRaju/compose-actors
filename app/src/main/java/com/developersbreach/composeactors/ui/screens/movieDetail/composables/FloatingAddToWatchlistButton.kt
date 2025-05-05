package com.developersbreach.composeactors.ui.screens.movieDetail.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.designsystem.components.CaIcon
import com.developersbreach.designsystem.components.CaTextSubtitle2

@Composable
fun FloatingAddToWatchlistButton(
    isInWatchlist: Boolean,
    addToWatchlist: () -> Unit,
    removeFromWatchlist: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val fabState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        ExtendedFloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.navigationBarsPadding(),
            onClick = {
                if (!isInWatchlist) {
                    addToWatchlist()
                } else {
                    removeFromWatchlist()
                }
            },
            icon = {
                CaIcon(
                    modifier = Modifier,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    imageVector = when {
                        isInWatchlist -> Icons.Filled.BookmarkRemove
                        else -> Icons.Outlined.BookmarkAdd
                    },
                )
            },
            text = {
                AnimatedVisibility(
                    visibleState = fabState,
                ) {
                    CaTextSubtitle2(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier,
                        text = when {
                            !isInWatchlist -> stringResource(R.string.add_to_watchlist_text)
                            else -> stringResource(R.string.remove_from_watchlist_text)
                        },
                    )
                }
            },
        )
    }
}