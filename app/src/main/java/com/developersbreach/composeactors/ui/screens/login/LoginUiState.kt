package com.developersbreach.composeactors.ui.screens.login

import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val loginCompleted: Boolean? = null,
)

sealed class LoginUiMessage : ErrorMessage {
    data object LoginError : LoginUiMessage()

    data class ValidationError(val message: String) : LoginUiMessage()

    data class Common(val error: CommonUiMessage) : LoginUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is LoginError -> UiMessageWithResId(R.string.login_error)
        is ValidationError -> UiMessageWithResId(R.string.login_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}