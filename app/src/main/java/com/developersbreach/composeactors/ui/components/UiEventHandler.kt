package com.developersbreach.composeactors.ui.components

import com.developersbreach.composeactors.domain.core.ErrorSeverity
import com.developersbreach.composeactors.domain.core.UserMessageKey

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

fun UiText.showMessage(
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

fun String.showMessage(
    actionType: ActionType? = null,
    action: (() -> Unit)? = null,
    duration: MessageDuration = MessageDuration.SHORT,
    severity: ErrorSeverity = ErrorSeverity.INFO,
): UiEvent.ShowMessage =
    UiText.DynamicString(this).showMessage(actionType, action, duration, severity)

fun UserMessageKey.showMessage(
    vararg args: Any,
    actionType: ActionType? = null,
    action: (() -> Unit)? = null,
    duration: MessageDuration = MessageDuration.SHORT,
    severity: ErrorSeverity = ErrorSeverity.INFO,
): UiEvent.ShowMessage =
    UiText.Message(this, args.toList()).showMessage(actionType, action, duration, severity)

fun UiText.showDialog(
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

fun String.showDialog(
    actionType: ActionType? = null,
    action: (() -> Unit)? = null,
    isDismissible: Boolean = true,
    severity: ErrorSeverity = ErrorSeverity.INFO,
): UiEvent.ShowDialog =
    UiText.DynamicString(this).showDialog(actionType, action, isDismissible, severity)

fun UserMessageKey.showDialog(
    vararg args: Any,
    actionType: ActionType? = null,
    action: (() -> Unit)? = null,
    isDismissible: Boolean = true,
    severity: ErrorSeverity = ErrorSeverity.INFO,
): UiEvent.ShowDialog =
    UiText.Message(this, args.toList()).showDialog(actionType, action, isDismissible, severity)
