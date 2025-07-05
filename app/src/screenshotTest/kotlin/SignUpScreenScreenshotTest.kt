package com.developersbreach.composeactors.screenshotTests

import androidx.compose.runtime.Composable
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.screens.signup.SignUpScreenAwaitingConfirmationStepPreview
import com.developersbreach.composeactors.ui.screens.signup.SignUpScreenCompletedStepPreview
import com.developersbreach.composeactors.ui.screens.signup.SignUpScreenInitialStepPreview

@PreviewLightDark
@Composable
private fun SignUpScreenInitialStepPreviewTest() {
    SignUpScreenInitialStepPreview()
}

@PreviewLightDark
@Composable
private fun SignUpScreenAwaitingConfirmationStepPreviewTest() {
    SignUpScreenAwaitingConfirmationStepPreview()
}

@PreviewLightDark
@Composable
private fun SignUpScreenCompletedStepPreviewTest() {
    SignUpScreenCompletedStepPreview()
}