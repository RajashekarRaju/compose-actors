package com.developersbreach.composeactors.ui.components

import com.developersbreach.composeactors.domain.core.ErrorSeverity

sealed class UiEvent {
    data class ShowMessage(
        val uiMessage: UiMessage,
        val duration: MessageDuration = MessageDuration.SHORT,
    ) : UiEvent()

    data class ShowDialog(
        val message: UiMessage,
        val isDismissible: Boolean = true,
    ) : UiEvent()

    data object NavigateBack : UiEvent()
}

enum class MessageDuration {
    SHORT,
    LONG,
    INDEFINITE,
}

fun String.showMessage(
    actionType: ActionType? = null,
    action: (() -> Unit)? = null,
    duration: MessageDuration = MessageDuration.SHORT,
    severity: ErrorSeverity = ErrorSeverity.INFO,
): UiEvent.ShowMessage {
    return UiEvent.ShowMessage(
        uiMessage = UiMessage(
            title = this,
            actionType = actionType,
            action = action,
            severity = severity,
        ),
        duration = duration,
    )
}

fun String.showDialog(
    actionType: ActionType? = null,
    action: (() -> Unit)? = null,
    isDismissible: Boolean = true,
    severity: ErrorSeverity = ErrorSeverity.INFO,
): UiEvent.ShowDialog {
    return UiEvent.ShowDialog(
        message = UiMessage(
            title = this,
            actionType = actionType,
            action = action,
            severity = severity,
        ),
        isDismissible = isDismissible,
    )
}