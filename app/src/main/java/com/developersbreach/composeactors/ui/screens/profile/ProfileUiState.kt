package com.developersbreach.composeactors.ui.screens.profile

import com.developersbreach.composeactors.data.region.model.Region

sealed class ProfileActions {
    data object NavigateToLogin : ProfileActions()

    data object UnauthenticatedUI : ProfileActions()

    data object GuestUI : ProfileActions()

    data class AuthenticatedUI(
        val name: String,
        val profilePictureUrl: String? = null,
    ) : ProfileActions()
}

data class ProfileUiState(
    val region: Region = Region.defaultRegion,
    val regions: List<Region> = emptyList(),
    val isDropdownExpanded: Boolean = false,
    val actions: ProfileActions? = null,
)