package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import timber.log.Timber

@Composable
fun <T> UiStateHandler(
    uiState: UiState<T>,
    isLoading: Boolean = false,
    snackbarHostState: SnackbarHostState? = null,
    onRetry: (() -> Unit)? = null,
    content: @Composable (T) -> Unit,
) {
    val context = LocalContext.current
    val shouldDismissErrorDialog = rememberSaveable { mutableStateOf(false) }
    val shouldShowInlineError = rememberSaveable { mutableStateOf(false) }
    val shouldShowBanner = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        shouldDismissErrorDialog.value = false
        shouldShowInlineError.value = false
        shouldShowBanner.value = false
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
                // Handle legacy Throwable errors by converting to AppError
                if (!shouldDismissErrorDialog.value) {
                    val errorDetails = uiState.throwable
                    Timber.e(errorDetails)
                    val appError = errorDetails.toAppError(context)
                    HandleAppError(
                        error = appError,
                        onRetry = onRetry,
                        onDismiss = { shouldDismissErrorDialog.value = true },
                        snackbarHostState = snackbarHostState,
                        shouldShowInlineError = shouldShowInlineError,
                        shouldShowBanner = shouldShowBanner,
                        shouldDismissErrorDialog = shouldDismissErrorDialog
                    )
                }
            }

            is UiState.AppErrorState -> {
                // Handle new AppError states with appropriate UI based on severity
                Timber.e(uiState.error.cause, "AppError: ${uiState.error}")
                HandleAppError(
                    error = uiState.error,
                    onRetry = onRetry,
                    onDismiss = { 
                        shouldDismissErrorDialog.value = true
                        shouldShowInlineError.value = false
                        shouldShowBanner.value = false
                    },
                    snackbarHostState = snackbarHostState,
                    shouldShowInlineError = shouldShowInlineError,
                    shouldShowBanner = shouldShowBanner,
                    shouldDismissErrorDialog = shouldDismissErrorDialog
                )
            }

            is UiState.Success -> content(uiState.data)
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colors.onBackground,
            )
        }
    }
}

@Composable
private fun HandleAppError(
    error: AppError,
    onRetry: (() -> Unit)?,
    onDismiss: () -> Unit,
    snackbarHostState: SnackbarHostState?,
    shouldShowInlineError: androidx.compose.runtime.MutableState<Boolean>,
    shouldShowBanner: androidx.compose.runtime.MutableState<Boolean>,
    shouldDismissErrorDialog: androidx.compose.runtime.MutableState<Boolean>
) {
    val context = LocalContext.current
    val errorMapper = ErrorMapper(context)

    when (error.getSeverity()) {
        ErrorSeverity.LOW -> {
            // Show inline error message for validation errors
            if (!shouldShowInlineError.value) {
                shouldShowInlineError.value = true
            }
            if (shouldShowInlineError.value) {
                InlineErrorMessage(
                    error = error,
                    onRetry = onRetry,
                    onDismiss = onDismiss
                )
            }
        }

        ErrorSeverity.MEDIUM -> {
            // Show snackbar for medium severity errors
            LaunchedEffect(error) {
                snackbarHostState?.showErrorSnackbar(
                    error = error,
                    context = context,
                    onRetry = onRetry
                )
            }
        }

        ErrorSeverity.HIGH -> {
            // Show blocking dialog for critical errors
            if (!shouldDismissErrorDialog.value) {
                ShowAlertDialog(
                    onButtonClick = onDismiss,
                    modifier = Modifier,
                    title = errorMapper.getErrorTitle(error),
                    description = errorMapper.getErrorMessage(error),
                )
            }
        }
    }
}
