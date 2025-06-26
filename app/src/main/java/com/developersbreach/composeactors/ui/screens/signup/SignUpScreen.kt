package com.developersbreach.composeactors.ui.screens.signup

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = viewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = viewModel.uiEvent,
        isLoading = viewModel.isLoading,
    ) { data ->
        val error = when (val state = viewModel.uiState) {
            is UiState.Error -> when (state.throwable.message) {
                "PASSWORD_MISMATCH" -> "Passwords do not match"
                "EMPTY_FIELDS" -> "Please fill all fields"
                "EMPTY_CODE" -> "Please enter the confirmation code"
                else -> state.throwable.localizedMessage ?: "Unknown error"
            }

            else -> null
        }
        SignUpScreenUI(
            onClickSignUp = { viewModel.signUp(data.email, data.password, data.confirmPassword) },
            onClickNavigateUp = navigateUp,
            navigateToHome = navigateToHome,
            login = { viewModel.login(data.email, data.password) },
            onEmailChange = { viewModel.onEmailChange(it) },
            onPasswordChange = { viewModel.onPasswordChange(it) },
            onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) },
            onPasswordToggledVisibilityChange = { viewModel.onPasswordToggledVisibilityChange() },
            onCodeChange = { viewModel.onCodeChange(it) },
            onClickConfirm = { viewModel.confirmSignUp(data.email, it) },
            data = data,
            scaffoldState = scaffoldState,
            error = error,
        )
    }
}