package com.developersbreach.composeactors.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    UiStateHandler(
        uiState = viewModel.uiState,
        isLoading = viewModel.isLoading,
    ) { data ->
        LaunchedEffect(data.loginCompleted) {
            if (data.loginCompleted == true) {
                navigateToHome()
            }
        }

        LoginScreenUI(
            onClickLogin = { viewModel.login(data.email, data.password) },
            onClickSkip = { viewModel.skipLogin() },
            onEmailChange = { viewModel.onEmailChange(it) },
            onPasswordChange = { viewModel.onPasswordChange(it) },
            onPasswordToggledVisibilityChange = { viewModel.onPasswordToggledVisibilityChange() },
            onClickSignUp = navigateToSignUp,
            data = data,
        )
    }
}