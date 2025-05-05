package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    UiStateHandler(
        uiState = viewModel.uiState,
    ) {
        ProfileScreenUI(
            navigateUp = navigateUp,
            onClickLogout = { viewModel.logout() },
            navigateToLogin = navigateToLogin,
            profileUiState = it,
        )
    }
}