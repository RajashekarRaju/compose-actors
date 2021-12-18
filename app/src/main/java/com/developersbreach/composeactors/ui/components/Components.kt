package com.developersbreach.composeactors.ui.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.utils.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Perform network check and show snackbar if offline.
 *
 * @param scaffoldState attach snackbar host state to the scaffold
 * @param scope Remember state of scaffold to manage snackbar
 */
@Composable
fun IfOfflineShowSnackbar(
    scaffoldState: ScaffoldState,
    context: Context,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val isOnline = NetworkManager(context).checkForActiveNetwork()
    if (!isOnline) {
        LaunchedEffect(scope) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = context.getString(R.string.offline_snackbar_message),
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }
}

@Composable
fun AppDivider(
    verticalPadding: Dp
) {
    Divider(
        color = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
        thickness = 1.dp,
        startIndent = 0.dp,
        modifier = Modifier.padding(vertical = verticalPadding)
    )
}

/**
 * @param title commonly used text in all screens to show title or header for category.
 */
@Composable
fun CategoryTitle(
    title: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .padding(start = 20.dp)
            .alpha(0.5f)
    )
}