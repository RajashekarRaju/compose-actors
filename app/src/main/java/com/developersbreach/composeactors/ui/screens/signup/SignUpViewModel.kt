package com.developersbreach.composeactors.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
) : ViewModel() {

    var uiState: UiState<SignUpData> by mutableStateOf(UiState.Success(SignUpData()))
        private set

    var isLoading by mutableStateOf(false)
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
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            uiState = UiState.Error(Throwable("Fields cannot be empty"))
            return
        }
        if (password != confirmPassword) {
            uiState = UiState.Error(Throwable("Passwords do not match"))
            return
        }
        viewModelScope.launch {
            isLoading = true
            uiState = authenticationService.signUp(
                email = email,
                password = password,
            ).fold(
                ifLeft = {
                    when {
                        it.message?.contains("CONFIRM_SIGN_UP_STEP") == true -> {
                            UiState.Success(
                                SignUpData(
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
                        SignUpData(signUpStep = SignUpStep.ConfirmationCompleted),
                    )
                },
            )
            isLoading = false
        }
    }

    fun confirmSignUp(
        email: String,
        code: String,
    ) {
        if (code.isEmpty()) {
            uiState = UiState.Error(Throwable("Verification code is required"))
            return
        }
        viewModelScope.launch {
            isLoading = true
            uiState = authenticationService.confirmSignUp(
                email = email,
                code = code,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    UiState.Success(
                        SignUpData(signUpStep = SignUpStep.ConfirmationCompleted),
                    )
                },
            )
            isLoading = false
        }
    }

    fun login(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            isLoading = true
            uiState = authenticationService.signIn(
                email = email,
                password = password,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    UiState.Success(
                        SignUpData(signUpStep = SignUpStep.LoginProcessCompleted),
                    )
                },
            )
            isLoading = false
        }
    }
}