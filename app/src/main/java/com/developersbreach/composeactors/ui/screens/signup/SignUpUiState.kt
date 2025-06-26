package com.developersbreach.composeactors.ui.screens.signup

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