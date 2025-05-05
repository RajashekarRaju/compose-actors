package com.developersbreach.composeactors.ui.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.domain.session.GetSessionState
import com.developersbreach.composeactors.domain.session.SessionState
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSessionState: GetSessionState,
) : ViewModel() {

    var uiState: UiState<SessionState> by mutableStateOf(UiState.Loading)
        private set

    init {
        checkUserSignInState()
    }

    private fun checkUserSignInState() {
        viewModelScope.launch {
            uiState = getSessionState().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { UiState.Success(it) },
            )
        }
    }
}