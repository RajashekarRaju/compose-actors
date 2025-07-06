package com.developersbreach.composeactors.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.domain.core.UserMessageKey
import com.developersbreach.composeactors.domain.core.SignInException
import com.developersbreach.composeactors.ui.components.UiText
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
                showMessage(UserMessageKey.FieldsEmpty)
                return@launch
            }
            if (password != confirmPassword) {
                showMessage(UserMessageKey.PasswordMismatch)
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
                showMessage(UserMessageKey.VerificationRequired)
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
                ifLeft = {
                    when (it) {
                        is SignInException -> showMessage(it.key)
                        else -> it.localizedMessage?.let { msg -> showMessage(UiText.DynamicString(msg)) }
                            ?: showMessage(UserMessageKey.FailedLogin)
                    }
                },
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