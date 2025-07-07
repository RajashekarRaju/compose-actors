package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.UiMessage.Companion.toMessage
import kotlinx.coroutines.flow.SharedFlow
import timber.log.Timber

@Composable
fun <T> UiStateHandler(
    uiState: UiState<T>,
    isLoading: Boolean = false,
    uiEvent: SharedFlow<UiEvent>,
    scaffoldState: ScaffoldState,
    content: @Composable (T) -> Unit,
) {
    val shouldDismissErrorDialog = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    var dialogMessage by remember { mutableStateOf<UiMessage?>(null) }

    LaunchedEffect(Unit) {
        shouldDismissErrorDialog.value = false
    }

    LaunchedEffect(uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowMessage -> scaffoldState.snackbarHostState.showSnackbar(
                    message = event.uiMessage.title.toMessage(context),
                    duration = when (event.duration) {
                        MessageDuration.SHORT -> androidx.compose.material.SnackbarDuration.Short
                        MessageDuration.LONG -> androidx.compose.material.SnackbarDuration.Long
                        MessageDuration.INDEFINITE -> androidx.compose.material.SnackbarDuration.Indefinite
                    },
                )

                is UiEvent.ShowDialog -> dialogMessage = event.message
                UiEvent.NavigateBack -> Unit
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.onBackground,
                )
            }

            is UiState.Error -> {
                if (!shouldDismissErrorDialog.value) {
                    val errorDetails = uiState.throwable
                    Timber.e(errorDetails)
                    ShowAlertDialog(
                        isDismissible = false,
                        onDismissRequest = { !shouldDismissErrorDialog.value },
                        modifier = Modifier,
                        title = "Error occurred",
                        description = errorDetails.localizedMessage ?: "Error information not available",
                    )
                }
            }

            is UiState.Success -> content(uiState.data)
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.onBackground,
            )
        }

        dialogMessage?.let {
            ShowAlertDialog(
                title = context.getString(R.string.unexpected_error),
                description = it.title.toMessage(context),
                isDismissible = it.isDismissible,
                onDismissRequest = { dialogMessage = null },
            )
        }
    }
}