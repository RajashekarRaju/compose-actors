package com.developersbreach.composeactors.ui.screens.login

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
class LoginViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
) : ViewModel() {

    var uiState: UiState<LoginData> by mutableStateOf(UiState.Success(LoginData()))
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

    fun onPasswordToggledVisibilityChange() {
        uiState = uiState.modifyLoadedState {
            copy(showPassword = !this.showPassword)
        }
    }

    fun login(
        email: String,
        password: String,
    ) {
        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            uiState = authenticationService.signIn(
                email = email,
                password = password,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { UiState.Success(LoginData(loginCompleted = true)) },
            )
            isLoading = false
        }
    }

    fun skipLogin() {
        viewModelScope.launch {
            isLoading = true
            uiState = authenticationService.skipLogin().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { UiState.Success(LoginData(loginCompleted = true)) },
            )
            isLoading = false
        }
    }
}