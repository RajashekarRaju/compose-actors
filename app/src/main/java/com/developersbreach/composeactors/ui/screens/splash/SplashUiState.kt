package com.developersbreach.composeactors.ui.screens.splash

sealed class SplashUiState {
    object Splash : SplashUiState()

    object NavigateToLogin : SplashUiState()

    object NavigateToHome : SplashUiState()
}