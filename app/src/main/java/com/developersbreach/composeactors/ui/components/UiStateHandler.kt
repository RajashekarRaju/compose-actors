package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.developersbreach.composeactors.ui.screens.search.SearchUiMessage
import com.developersbreach.composeactors.ui.screens.search.toResId
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

    LaunchedEffect(Unit) {
        shouldDismissErrorDialog.value = false
    }

    LaunchedEffect(Unit) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowMessage -> {
                    when (val message = event.message) {
                        is String -> message
                        is SearchUiMessage -> context.getString(message.toResId())
                        else -> message.toString()
                    }.let {
                        scaffoldState.snackbarHostState.showSnackbar(it)
                    }
                }
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
                        onButtonClick = { shouldDismissErrorDialog.value = true },
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
    }
}