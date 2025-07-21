package com.developersbreach.composeactors.ui.screens.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AppRegistration
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaButtonFilled
import com.developersbreach.designsystem.components.CaButtonOutlined
import com.developersbreach.designsystem.components.CaImage
import com.developersbreach.designsystem.components.CaOutlinedTextField
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaTextBody1
import com.developersbreach.designsystem.components.CaTextFieldIconConfig
import com.developersbreach.designsystem.components.CaTextH5
import com.developersbreach.designsystem.components.CaVerticalSpacer

@Composable
fun ForgotPasswordScreenUI(
    onClickRequestReset: (String) -> Unit,
    onClickConfirmReset: (String, String, String, String) -> Unit,
    onClickNavigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
    onEmailChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmNewPasswordChange: (String) -> Unit,
    onPasswordToggledVisibilityChange: () -> Unit,
    data: ForgotPasswordUiState,
    scaffoldState: ScaffoldState,
) {
    CaScaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            CaImage(
                painter = painterResource(id = R.drawable.login_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp, start = 20.dp, end = 20.dp),
            ) {
                CaImage(
                    painter = painterResource(id = R.drawable.login_logo),
                    contentDescription = null,
                    modifier = Modifier,
                )

                CaVerticalSpacer(value = 28)

                when (data.step) {
                    is ForgotPasswordStep.RequestReset -> {
                        RequestResetUI(
                            email = data.email,
                            onEmailChange = onEmailChange,
                            onClickRequestReset = onClickRequestReset,
                            onClickNavigateUp = onClickNavigateUp,
                        )
                    }

                    is ForgotPasswordStep.ConfirmReset -> {
                        ConfirmResetUI(
                            email = data.step.email,
                            code = data.code,
                            newPassword = data.newPassword,
                            confirmNewPassword = data.confirmNewPassword,
                            showPassword = data.showPassword,
                            onCodeChange = onCodeChange,
                            onNewPasswordChange = onNewPasswordChange,
                            onConfirmNewPasswordChange = onConfirmNewPasswordChange,
                            onPasswordToggledVisibilityChange = onPasswordToggledVisibilityChange,
                            onClickConfirmReset = onClickConfirmReset,
                        )
                    }

                    ForgotPasswordStep.ResetCompleted -> {
                        ResetCompletedUI(navigateToLogin)
                    }
                }
            }
        }
    }
}

@Composable
private fun RequestResetUI(
    email: String,
    onEmailChange: (String) -> Unit,
    onClickRequestReset: (String) -> Unit,
    onClickNavigateUp: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        CaTextH5(
            text = stringResource(R.string.forgot_password),
            modifier = Modifier,
            color = MaterialTheme.colors.primary,
        )

        CaVerticalSpacer(value = 16)

        CaTextBody1(
            text = stringResource(R.string.forgot_password_message),
            modifier = Modifier,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1.copy(
                textAlign = TextAlign.Center,
            ),
        )

        CaVerticalSpacer(value = 16)

        CaOutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            leadingIcon = CaTextFieldIconConfig(
                modifier = Modifier.padding(start = 4.dp),
                iconModifier = Modifier,
                imageVector = Icons.Rounded.Email,
                tint = MaterialTheme.colors.onBackground,
            ),
            placeholderText = stringResource(R.string.email),
            modifier = Modifier.fillMaxWidth(),
        )

        CaVerticalSpacer(value = 16)

        CaButtonFilled(
            title = stringResource(R.string.request_reset_code),
            onClick = { onClickRequestReset(email) },
            modifier = Modifier.fillMaxWidth(),
        )

        CaVerticalSpacer(value = 4)

        CaButtonOutlined(
            title = stringResource(R.string.cancel),
            onClick = onClickNavigateUp,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ConfirmResetUI(
    email: String,
    code: String,
    newPassword: String,
    confirmNewPassword: String,
    showPassword: Boolean,
    onCodeChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmNewPasswordChange: (String) -> Unit,
    onPasswordToggledVisibilityChange: () -> Unit,
    onClickConfirmReset: (String, String, String, String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        CaTextH5(
            text = stringResource(R.string.reset_password),
            modifier = Modifier,
        )

        CaVerticalSpacer(value = 16)

        CaTextBody1(
            text = stringResource(R.string.reset_password_message),
            modifier = Modifier,
        )

        CaVerticalSpacer(value = 16)

        CaOutlinedTextField(
            value = code,
            onValueChange = onCodeChange,
            leadingIcon = CaTextFieldIconConfig(
                modifier = Modifier.padding(start = 4.dp),
                iconModifier = Modifier,
                imageVector = Icons.Rounded.AppRegistration,
                tint = MaterialTheme.colors.onBackground,
            ),
            placeholderText = stringResource(R.string.verification_code),
            modifier = Modifier.fillMaxWidth(),
        )

        CaVerticalSpacer(value = 4)

        CaOutlinedTextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            leadingIcon = CaTextFieldIconConfig(
                modifier = Modifier.padding(start = 4.dp),
                iconModifier = Modifier,
                imageVector = Icons.Rounded.Password,
                tint = MaterialTheme.colors.onBackground,
            ),
            trailingIcon = CaTextFieldIconConfig(
                modifier = Modifier.padding(end = 4.dp),
                iconModifier = Modifier,
                onClick = onPasswordToggledVisibilityChange,
                tint = MaterialTheme.colors.onBackground,
                imageVector = when {
                    showPassword -> Icons.Rounded.Visibility
                    else -> Icons.Rounded.VisibilityOff
                },
            ),
            placeholderText = stringResource(R.string.new_password),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = when {
                showPassword -> VisualTransformation.None
                else -> PasswordVisualTransformation()
            },
        )

        CaVerticalSpacer(value = 4)

        CaOutlinedTextField(
            value = confirmNewPassword,
            onValueChange = onConfirmNewPasswordChange,
            leadingIcon = CaTextFieldIconConfig(
                modifier = Modifier.padding(start = 4.dp),
                iconModifier = Modifier,
                imageVector = Icons.Rounded.Password,
                tint = MaterialTheme.colors.onBackground,
            ),
            placeholderText = stringResource(R.string.confirm_new_password),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = when {
                showPassword -> VisualTransformation.None
                else -> PasswordVisualTransformation()
            },
        )

        CaVerticalSpacer(value = 16)

        CaButtonFilled(
            title = stringResource(R.string.reset_password),
            onClick = { onClickConfirmReset(email, code, newPassword, confirmNewPassword) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ResetCompletedUI(
    navigateToLogin: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        CaTextH5(
            text = stringResource(R.string.password_reset_successful),
            modifier = Modifier,
            color = MaterialTheme.colors.primary,
        )

        CaVerticalSpacer(value = 16)

        CaTextBody1(
            text = stringResource(R.string.password_reset_message),
            modifier = Modifier,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1.copy(
                textAlign = TextAlign.Center,
            ),
        )

        CaVerticalSpacer(value = 16)

        CaButtonFilled(
            title = stringResource(R.string.go_to_login),
            onClick = navigateToLogin,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ForgotPasswordScreenPreview(
    uiState: ForgotPasswordUiState,
) {
    ComposeActorsTheme {
        ForgotPasswordScreenUI(
            onClickRequestReset = {},
            onClickConfirmReset = { _, _, _, _ -> },
            onClickNavigateUp = {},
            navigateToLogin = {},
            onEmailChange = {},
            onCodeChange = {},
            onNewPasswordChange = {},
            onConfirmNewPasswordChange = {},
            onPasswordToggledVisibilityChange = {},
            data = uiState,
            scaffoldState = rememberScaffoldState(),
        )
    }
}

@PreviewLightDark
@Composable
fun ForgotPasswordScreenRequestResetPreview() {
    ForgotPasswordScreenPreview(
        uiState = ForgotPasswordUiState(
            email = "example@email.com",
            step = ForgotPasswordStep.RequestReset,
        ),
    )
}

@PreviewLightDark
@Composable
fun ForgotPasswordScreenConfirmResetPreview() {
    ForgotPasswordScreenPreview(
        uiState = ForgotPasswordUiState(
            code = "123456",
            newPassword = "newpassword",
            confirmNewPassword = "newpassword",
            step = ForgotPasswordStep.ConfirmReset("example@email.com"),
        ),
    )
}

@PreviewLightDark
@Composable
fun ForgotPasswordScreenResetCompletedPreview() {
    ForgotPasswordScreenPreview(
        uiState = ForgotPasswordUiState(
            code = "123456",
            newPassword = "newpassword",
            confirmNewPassword = "newpassword",
            step = ForgotPasswordStep.ResetCompleted,
        ),
    )
}