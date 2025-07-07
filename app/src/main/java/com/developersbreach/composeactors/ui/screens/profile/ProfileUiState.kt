package com.developersbreach.composeactors.ui.screens.profile

sealed class ProfileUiState {
    data object NavigateToLogin : ProfileUiState()

    data object UnauthenticatedUI : ProfileUiState()

    data object GuestUI : ProfileUiState()

    data class AuthenticatedUI(
        val name: String,
        val profilePictureUrl: String? = null,
    ) : ProfileUiState()
}