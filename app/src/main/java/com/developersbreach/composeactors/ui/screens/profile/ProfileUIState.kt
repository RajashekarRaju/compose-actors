package com.developersbreach.composeactors.ui.screens.profile

sealed class ProfileUIState {
    data object NavigateToLogin : ProfileUIState()

    data object UnauthenticatedUI : ProfileUIState()

    data object GuestUI : ProfileUIState()

    data class AuthenticatedUI(
        val name: String,
        val profilePictureUrl: String? = null,
    ) : ProfileUIState()
}