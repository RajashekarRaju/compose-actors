package com.developersbreach.composeactors.ui.screens.signup

import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val showPassword: Boolean = false,
    val signUpStep: SignUpStep = SignUpStep.Initial,
)

sealed class SignUpStep {
    data object Initial : SignUpStep()

    data class AwaitingConfirmation(
        val email: String,
        val password: String,
        val code: String = "",
    ) : SignUpStep()

    data object ConfirmationCompleted : SignUpStep()

    data object LoginProcessCompleted : SignUpStep()
}

sealed class SignUpUiMessage : ErrorMessage {
    data object SignUpError : SignUpUiMessage()

    data class ValidationError(val message: String) : SignUpUiMessage()

    data class Common(val error: CommonUiMessage) : SignUpUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is SignUpError -> UiMessageWithResId(R.string.signup_error)
        is ValidationError -> UiMessageWithResId(R.string.signup_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}