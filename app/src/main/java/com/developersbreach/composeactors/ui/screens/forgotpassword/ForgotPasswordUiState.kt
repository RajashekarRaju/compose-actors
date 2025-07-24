package com.developersbreach.composeactors.ui.screens.forgotpassword

data class ForgotPasswordUiState(
    val email: String = "",
    val code: String = "",
    val newPassword: String = "",
    val confirmNewPassword: String = "",
    val showPassword: Boolean = false,
    val step: ForgotPasswordStep = ForgotPasswordStep.RequestReset,
)

sealed class ForgotPasswordStep {
    data object RequestReset : ForgotPasswordStep()

    data class ConfirmReset(val email: String) : ForgotPasswordStep()

    data object ResetCompleted : ForgotPasswordStep()
}