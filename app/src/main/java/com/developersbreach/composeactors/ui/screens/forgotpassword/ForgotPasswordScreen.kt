package com.developersbreach.composeactors.ui.screens.forgotpassword

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = viewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = viewModel.uiEvent,
        isLoading = viewModel.isLoading,
    ) { data ->
        ForgotPasswordScreenUI(
            onClickNavigateUp = navigateUp,
            navigateToLogin = navigateToLogin,
            onEmailChange = viewModel::onEmailChange,
            onCodeChange = viewModel::onCodeChange,
            onNewPasswordChange = viewModel::onNewPasswordChange,
            onConfirmNewPasswordChange = viewModel::onConfirmNewPasswordChange,
            onPasswordToggledVisibilityChange = viewModel::onPasswordToggledVisibilityChange,
            data = data,
            scaffoldState = scaffoldState,
            onClickRequestReset = { email -> viewModel.requestPasswordReset(email) },
            onClickConfirmReset = { email, code, newPassword, confirmNewPassword ->
                viewModel.confirmPasswordReset(email, code, newPassword, confirmNewPassword)
            },
        )
    }
}