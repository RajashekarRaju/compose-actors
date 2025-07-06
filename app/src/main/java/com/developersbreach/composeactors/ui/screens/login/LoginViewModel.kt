package com.developersbreach.composeactors.ui.screens.login

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
class LoginViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    var uiState: UiState<LoginUiState> by mutableStateOf(UiState.Success(LoginUiState()))
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

    fun onPasswordToggledVisibilityChange() {
        uiState = uiState.modifyLoadedState {
            copy(showPassword = !this.showPassword)
        }
    }

    fun login(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()) {
                showMessage(UserMessageKey.InvalidEmailOrPassword)
                return@launch
            }
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
                ifRight = { uiState = UiState.Success(LoginUiState(loginCompleted = true)) },
            )
            hideLoading()
        }
    }

    fun skipLogin() {
        viewModelScope.launch {
            showLoading()
            uiState = authenticationService.skipLogin().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { UiState.Success(LoginUiState(loginCompleted = true)) },
            )
            hideLoading()
        }
    }
}