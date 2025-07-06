package com.developersbreach.composeactors.ui.components

import android.content.Context
import com.developersbreach.composeactors.domain.core.AppError
import com.developersbreach.composeactors.domain.core.ErrorMessage
import com.developersbreach.composeactors.domain.core.ErrorSeverity
import com.developersbreach.composeactors.domain.core.UserMessageKey

data class UiMessage(
    val title: UiText,
    val actionType: ActionType? = null,
    val action: (() -> Unit)? = null,
    val severity: ErrorSeverity = ErrorSeverity.INFO,
    val isDismissible: Boolean = true,
) {
    companion object {
        fun ErrorMessage.toUiMessage(): UiMessage = when (this) {
            is AppError.NetworkError -> UiMessage(
                title = UiText.Message(UserMessageKey.NetworkError),
                actionType = ActionType.Retry,
                severity = severity,
            )

            is AppError.CriticalError -> UiMessage(
                title = UiText.Message(UserMessageKey.UnexpectedError),
                actionType = ActionType.Retry,
                severity = severity,
            )

            else -> UiMessage(
                title = UiText.DynamicString(message),
                severity = severity,
            )
        }

        fun UiText.toMessage(context: Context): String {
            return asString(context)
        }
    }
}

enum class ActionType {
    Retry,
    Logout,
}