package com.developersbreach.composeactors.ui.screens.splash

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = viewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = viewModel.uiEvent,
        isLoading = viewModel.isLoading,
    ) { data ->
        when (data) {
            SplashUiState.NavigateToLogin -> LaunchedEffect(data) { navigateToLogin() }
            SplashUiState.NavigateToHome -> LaunchedEffect(data) { navigateToHome() }
            SplashUiState.Splash -> SplashScreenUI()
        }
    }
}