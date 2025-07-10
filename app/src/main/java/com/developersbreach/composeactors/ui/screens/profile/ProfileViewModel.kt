package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import arrow.core.raise.either
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.data.region.model.Region
import com.developersbreach.composeactors.data.region.repository.RegionRepository
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.domain.session.GetSessionState
import com.developersbreach.composeactors.domain.session.SessionState
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getSessionState: GetSessionState,
    private val authenticationService: AuthenticationService,
    private val regionRepository: RegionRepository,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    var uiState: UiState<ProfileUiState> by mutableStateOf(UiState.Loading)
        private set

    init {
        checkUserSignInState()
    }

    private fun checkUserSignInState() {
        viewModelScope.launch {
            showLoading()
            uiState = either {
                val sessionState = async { getSessionState().bind() }
                val region = async { regionRepository.getRegion().bind() }
                val regions = async { regionRepository.getRegions().bind() }
                UiState.Success(
                    ProfileUiState(
                        region = region.await(),
                        regions = regions.await(),
                        actions = when (sessionState.await()) {
                            SessionState.Unauthenticated -> ProfileActions.UnauthenticatedUI
                            SessionState.Guest -> ProfileActions.GuestUI
                            SessionState.Authenticated -> {
                                val profile = authenticationService.getCurrentUser().bind()
                                ProfileActions.AuthenticatedUI(profile.name.orEmpty())
                            }
                        },
                    ),
                )
            }.fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { it },
            )
            hideLoading()
        }
    }

    fun logout() {
        viewModelScope.launch {
            showLoading()
            authenticationService.signOut().fold(
                ifLeft = { showDialog("Failed to logout") },
                ifRight = { uiState = UiState.Success(ProfileUiState(actions = ProfileActions.NavigateToLogin)) },
            )
            hideLoading()
        }
    }

    fun updateRegion(
        region: Region,
    ) {
        viewModelScope.launch {
            showLoading()
            regionRepository.updateRegion(
                region = region,
            ).fold(
                ifLeft = { showMessage("Failed to update region") },
                ifRight = {
                    uiState = uiState.modifyLoadedState {
                        copy(
                            region = region,
                            isDropdownExpanded = !isDropdownExpanded,
                        )
                    }
                },
            )
            hideLoading()
        }
    }

    fun onRegionDropdownToggle() {
        uiState = uiState.modifyLoadedState {
            copy(isDropdownExpanded = !isDropdownExpanded)
        }
    }
}