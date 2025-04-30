package com.developersbreach.composeactors.ui.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
) : ViewModel() {

    var uiState: UiState<SessionState> by mutableStateOf(UiState.Loading)
        private set

    init {
        checkUserSignInState()
    }

    private fun checkUserSignInState() {
        viewModelScope.launch {
            uiState = authenticationService.isGuestUser().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { isGuest ->
                    when {
                        isGuest -> UiState.Success(SessionState.Guest)
                        else -> getSessionState()
                    }
                },
            )
        }
    }

    private suspend fun getSessionState(): UiState<SessionState> {
        return authenticationService.isUserSignedIn().fold(
            ifLeft = { UiState.Error(it) },
            ifRight = { signedIn ->
                when {
                    signedIn -> UiState.Success(SessionState.Authenticated)
                    else -> UiState.Success(SessionState.Unauthenticated)
                }
            },
        )
    }
}