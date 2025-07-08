package com.developersbreach.composeactors.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.developersbreach.composeactors.domain.core.ErrorMessage
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.domain.core.ErrorSeverity
import com.developersbreach.composeactors.domain.core.toAppError
import com.developersbreach.composeactors.ui.components.UiMessage.Companion.toUiMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    protected val errorReporter: ErrorReporter,
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private var lastAction: (suspend () -> Unit)? = null

    protected fun showLoading() {
        isLoading = true
    }

    protected fun hideLoading() {
        isLoading = false
    }

    protected suspend fun emitUiEvent(event: UiEvent) {
        _uiEvent.emit(event)
    }

    protected suspend fun handleError(error: ErrorMessage) {
        if (error.severity == ErrorSeverity.CRITICAL) {
            errorReporter.reportError(error)
        }

        val baseMsg = error.toUiMessage()
        val uiMsg = when {
            error.severity != ErrorSeverity.INFO -> {
                baseMsg.copy(action = { retryLastAction() })
            }

            else -> baseMsg
        }

        when (error.severity) {
            ErrorSeverity.CRITICAL -> UiEvent.ShowDialog(uiMsg)
            else -> UiEvent.ShowMessage(uiMsg)
        }.let {
            emitUiEvent(it)
        }
    }

    protected fun <T> execute(
        action: suspend () -> T,
        onSuccess: suspend (T) -> Unit,
    ) {
        viewModelScope.launch {
            lastAction = { execute(action, onSuccess) }
            showLoading()
            try {
                val result = action()
                onSuccess(result)
            } catch (t: Throwable) {
                val error = (t as? ErrorMessage) ?: t.toAppError()
                handleError(error)
            } finally {
                hideLoading()
            }
        }
    }

    protected fun <T> executeEither(
        action: suspend () -> Either<Throwable, T>,
        onSuccess: suspend (T) -> Unit,
    ) {
        viewModelScope.launch {
            lastAction = { executeEither(action, onSuccess) }
            showLoading()
            try {
                action().fold(
                    ifLeft = { handleError(it.toAppError()) },
                    ifRight = { onSuccess(it) },
                )
            } catch (t: Throwable) {
                val error = (t as? ErrorMessage) ?: t.toAppError()
                handleError(error)
            } finally {
                lastAction = null
                hideLoading()
            }
        }
    }

    private fun retryLastAction() {
        lastAction?.let {
            viewModelScope.launch { it() }
        }
    }

    protected suspend fun showMessage(
        message: String,
        actionType: ActionType? = null,
        action: (() -> Unit)? = null,
        duration: MessageDuration = MessageDuration.SHORT,
        severity: ErrorSeverity = ErrorSeverity.INFO,
    ) {
        emitUiEvent(
            event = message.showMessage(
                actionType = actionType,
                action = action,
                severity = severity,
                duration = duration,
            ),
        )
    }

    protected suspend fun showDialog(
        message: String,
        actionType: ActionType? = null,
        action: (() -> Unit)? = null,
        severity: ErrorSeverity = ErrorSeverity.INFO,
        isDismissible: Boolean = true,
    ) {
        emitUiEvent(
            event = message.showDialog(
                actionType = actionType,
                action = action,
                severity = severity,
                isDismissible = isDismissible,
            ),
        )
    }
}