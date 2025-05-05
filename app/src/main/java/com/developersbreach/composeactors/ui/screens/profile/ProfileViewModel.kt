package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.domain.session.GetSessionState
import com.developersbreach.composeactors.domain.session.SessionState
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getSessionState: GetSessionState,
    private val authenticationService: AuthenticationService,
) : ViewModel() {

    var uiState: UiState<ProfileUIState> by mutableStateOf(UiState.Loading)
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
                        SessionState.Unauthenticated -> UiState.Success(ProfileUIState.UnauthenticatedUI)
                        SessionState.Guest -> UiState.Success(ProfileUIState.GuestUI)
                        SessionState.Authenticated -> updateProfileData()
                    }
                },
            )
        }
    }

    private suspend fun updateProfileData(): UiState<ProfileUIState.AuthenticatedUI> {
        return authenticationService.getCurrentUser().fold(
            ifLeft = { UiState.Error(it) },
            ifRight = {
                UiState.Success(ProfileUIState.AuthenticatedUI(it.name ?: ""))
            },
        )
    }

    fun logout() {
        viewModelScope.launch {
            uiState = authenticationService.signOut().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { UiState.Success(ProfileUIState.NavigateToLogin) },
            )
        }
    }
}