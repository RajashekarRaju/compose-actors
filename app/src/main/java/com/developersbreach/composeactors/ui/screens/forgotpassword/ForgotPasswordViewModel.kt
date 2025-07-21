package com.developersbreach.composeactors.ui.screens.forgotpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    var uiState: UiState<ForgotPasswordUiState> by mutableStateOf(UiState.Success(ForgotPasswordUiState()))
        private set

    fun onEmailChange(email: String) {
        uiState = uiState.modifyLoadedState {
            copy(email = email)
        }
    }

    fun onCodeChange(code: String) {
        uiState = uiState.modifyLoadedState {
            copy(code = code)
        }
    }

    fun onNewPasswordChange(password: String) {
        uiState = uiState.modifyLoadedState {
            copy(newPassword = password)
        }
    }

    fun onConfirmNewPasswordChange(password: String) {
        uiState = uiState.modifyLoadedState {
            copy(confirmNewPassword = password)
        }
    }

    fun onPasswordToggledVisibilityChange() {
        uiState = uiState.modifyLoadedState {
            copy(showPassword = !this.showPassword)
        }
    }

    fun requestPasswordReset(email: String) {
        viewModelScope.launch {
            if (email.isEmpty()) {
                showMessage("Email is required")
                return@launch
            }
            showLoading()
            authenticationService.forgotPassword(
                email = email,
            ).fold(
                ifLeft = { showMessage(it.localizedMessage ?: "Failed to request password reset") },
                ifRight = {
                    uiState = uiState.modifyLoadedState {
                        copy(step = ForgotPasswordStep.ConfirmReset(email))
                    }
                },
            )
            hideLoading()
        }
    }

    fun confirmPasswordReset(
        email: String,
        code: String,
        newPassword: String,
        confirmNewPassword: String,
    ) {
        viewModelScope.launch {
            if (code.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                showMessage("All fields are required")
                return@launch
            }

            if (newPassword != confirmNewPassword) {
                showMessage("Passwords do not match")
                return@launch
            }

            showLoading()
            authenticationService.confirmForgotPassword(
                email = email,
                code = code,
                newPassword = newPassword,
            ).fold(
                ifLeft = { showMessage(it.localizedMessage ?: "Failed to reset password") },
                ifRight = {
                    uiState = uiState.modifyLoadedState {
                        copy(step = ForgotPasswordStep.ResetCompleted)
                    }
                },
            )
            hideLoading()
        }
    }
}