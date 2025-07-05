package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.domain.session.GetSessionState
import com.developersbreach.composeactors.domain.session.SessionState
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getSessionState: GetSessionState,
    private val authenticationService: AuthenticationService,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    var uiState: UiState<ProfileUiState> by mutableStateOf(UiState.Loading)
        private set

    init {
        checkUserSignInState()
    }

    private fun checkUserSignInState() {
        viewModelScope.launch {
            uiState = getSessionState().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    when (it) {
                        SessionState.Unauthenticated -> UiState.Success(ProfileUiState.UnauthenticatedUI)
                        SessionState.Guest -> UiState.Success(ProfileUiState.GuestUI)
                        SessionState.Authenticated -> updateProfileData()
                    }
                },
            )
        }
    }

    private suspend fun updateProfileData(): UiState<ProfileUiState.AuthenticatedUI> {
        return authenticationService.getCurrentUser().fold(
            ifLeft = { UiState.Error(it) },
            ifRight = {
                UiState.Success(ProfileUiState.AuthenticatedUI(it.name ?: ""))
            },
        )
    }

    fun logout() {
        viewModelScope.launch {
            uiState = authenticationService.signOut().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { UiState.Success(ProfileUiState.NavigateToLogin) },
            )
        }
    }
}