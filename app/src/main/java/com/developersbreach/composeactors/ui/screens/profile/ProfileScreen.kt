package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = viewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = viewModel.uiEvent,
        isLoading = viewModel.isLoading,
    ) {
        ProfileScreenUI(
            navigateUp = navigateUp,
            onClickLogout = { viewModel.logout() },
            navigateToLogin = navigateToLogin,
            profileUiState = it,
            scaffoldState = scaffoldState,
        )
    }
}