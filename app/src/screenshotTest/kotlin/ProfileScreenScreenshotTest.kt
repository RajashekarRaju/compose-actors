package com.developersbreach.composeactors.screenshotTests

import androidx.compose.runtime.Composable
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.screens.profile.ProfileScreenAuthenticatedPreview
import com.developersbreach.composeactors.ui.screens.profile.ProfileScreenGuestPreview
import com.developersbreach.composeactors.ui.screens.profile.ProfileScreenUnauthenticatedPreview

@PreviewLightDark
@Composable
private fun ProfileScreenAuthenticatedPreviewTest() {
    ProfileScreenAuthenticatedPreview()
}

@PreviewLightDark
@Composable
private fun ProfileScreenUnauthenticatedPreviewTest() {
    ProfileScreenUnauthenticatedPreview()
}

@PreviewLightDark
@Composable
private fun ProfileScreenGuestPreviewTest() {
    ProfileScreenGuestPreview()
}