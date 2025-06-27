package com.developersbreach.composeactors.ui.screens.profile

import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId

sealed class ProfileUiState {
    data object NavigateToLogin : ProfileUiState()

    data object UnauthenticatedUI : ProfileUiState()

    data object GuestUI : ProfileUiState()

    data class AuthenticatedUI(
        val name: String,
        val profilePictureUrl: String? = null,
    ) : ProfileUiState()
}

sealed class ProfileUiMessage : ErrorMessage {
    data object ProfileError : ProfileUiMessage()

    data class ValidationError(val message: String) : ProfileUiMessage()

    data class Common(val error: CommonUiMessage) : ProfileUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is ProfileError -> UiMessageWithResId(R.string.profile_error)
        is ValidationError -> UiMessageWithResId(R.string.profile_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}