package com.developersbreach.composeactors.ui.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.domain.session.GetSessionState
import com.developersbreach.composeactors.domain.session.SessionState
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSessionState: GetSessionState,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    var uiState: UiState<SplashUiState> by mutableStateOf(UiState.Success(SplashUiState.Splash))
        private set

    init {
        checkUserSignInState()
    }

    private fun checkUserSignInState() {
        viewModelScope.launch {
            uiState = getSessionState().fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { sessionState ->
                    when (sessionState) {
                        SessionState.Authenticated -> SplashUiState.NavigateToHome
                        SessionState.Unauthenticated -> SplashUiState.NavigateToLogin
                        SessionState.Guest -> SplashUiState.NavigateToHome
                    }.let {
                        UiState.Success(it)
                    }
                },
            )
        }
    }
}