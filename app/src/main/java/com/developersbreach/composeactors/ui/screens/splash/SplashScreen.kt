package com.developersbreach.composeactors.ui.screens.splash

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
    UiStateHandler(
        uiState = viewModel.uiState,
    ) { sessionState: SessionState ->
        LaunchedEffect(sessionState) {
            when (sessionState) {
                SessionState.Authenticated -> navigateToHome()
                SessionState.Unauthenticated -> navigateToLogin()
                SessionState.Guest -> navigateToHome()
            }
        }

        SplashScreenUI()
    }
}