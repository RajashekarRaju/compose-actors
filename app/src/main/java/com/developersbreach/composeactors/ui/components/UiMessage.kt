package com.developersbreach.composeactors.ui.components

import android.content.Context
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.domain.core.AppError
import com.developersbreach.composeactors.domain.core.ErrorMessage
import com.developersbreach.composeactors.domain.core.ErrorSeverity

data class UiMessage(
    val title: Any,
    val actionType: ActionType? = null,
    val action: (() -> Unit)? = null,
    val severity: ErrorSeverity = ErrorSeverity.INFO,
    val isDismissible: Boolean = true,
) {
    companion object {
        fun ErrorMessage.toUiMessage(): UiMessage = when (this) {
            is AppError.NetworkError -> UiMessage(
                title = R.string.network_error,
                actionType = ActionType.Retry,
                severity = severity,
            )

            is AppError.CriticalError -> UiMessage(
                title = R.string.unexpected_error,
                actionType = ActionType.Retry,
                severity = severity,
            )

            else -> UiMessage(
                title = message,
                severity = severity,
            )
        }

        fun Any.toMessage(
            context: Context,
        ): String {
            return when (this) {
                is Int -> context.getString(this)
                else -> this.toString()
            }
        }
    }
}

enum class ActionType {
    Retry,
    Logout,
}