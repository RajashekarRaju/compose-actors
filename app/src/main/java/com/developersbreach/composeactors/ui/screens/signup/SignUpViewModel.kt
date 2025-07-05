package com.developersbreach.composeactors.ui.screens.signup

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
class SignUpViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    var uiState: UiState<SignUpUiState> by mutableStateOf(UiState.Success(SignUpUiState()))
        private set

    fun onEmailChange(
        email: String,
    ) {
        uiState = uiState.modifyLoadedState {
            copy(email = email)
        }
    }

    fun onPasswordChange(
        password: String,
    ) {
        uiState = uiState.modifyLoadedState {
            copy(password = password)
        }
    }

    fun onConfirmPasswordChange(
        confirmPassword: String,
    ) {
        uiState = uiState.modifyLoadedState {
            copy(confirmPassword = confirmPassword)
        }
    }

    fun onPasswordToggledVisibilityChange() {
        uiState = uiState.modifyLoadedState {
            copy(showPassword = !this.showPassword)
        }
    }

    fun onCodeChange(
        code: String,
    ) {
        uiState = uiState.modifyLoadedState {
            copy(
                signUpStep = when (signUpStep) {
                    is SignUpStep.AwaitingConfirmation -> (signUpStep).copy(code = code)
                    else -> signUpStep
                },
            )
        }
    }

    fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showMessage("Fields cannot be empty")
                return@launch
            }
            if (password != confirmPassword) {
                showMessage("Passwords do not match")
                return@launch
            }
            showLoading()
            uiState = authenticationService.signUp(
                email = email,
                password = password,
            ).fold(
                ifLeft = {
                    when {
                        it.message?.contains("CONFIRM_SIGN_UP_STEP") == true -> {
                            UiState.Success(
                                SignUpUiState(
                                    email = email,
                                    password = password,
                                    signUpStep = SignUpStep.AwaitingConfirmation(email, password),
                                ),
                            )
                        }

                        else -> UiState.Error(it)
                    }
                },
                ifRight = {
                    UiState.Success(
                        SignUpUiState(signUpStep = SignUpStep.ConfirmationCompleted),
                    )
                },
            )
            hideLoading()
        }
    }

    fun confirmSignUp(
        email: String,
        code: String,
    ) {
        viewModelScope.launch {
            if (code.isEmpty()) {
                showMessage("Verification code is required")
                return@launch
            }
            showLoading()
            uiState = authenticationService.confirmSignUp(
                email = email,
                code = code,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    UiState.Success(
                        SignUpUiState(signUpStep = SignUpStep.ConfirmationCompleted),
                    )
                },
            )
            hideLoading()
        }
    }

    fun login(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            showLoading()
            authenticationService.signIn(
                email = email,
                password = password,
            ).fold(
                ifLeft = { showMessage(it.localizedMessage ?: "Failed to login") },
                ifRight = {
                    uiState = UiState.Success(
                        SignUpUiState(signUpStep = SignUpStep.LoginProcessCompleted),
                    )
                },
            )
            hideLoading()
        }
    }
}