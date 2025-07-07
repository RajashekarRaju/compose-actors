package com.developersbreach.composeactors.ui.screens.signup

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaButtonFilled
import com.developersbreach.designsystem.components.CaOutlinedTextField
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaSurface
import com.developersbreach.designsystem.components.CaTextBody1
import com.developersbreach.designsystem.components.CaTextFieldIconConfig
import com.developersbreach.designsystem.components.CaTextH5
import com.developersbreach.designsystem.components.CaTextH6
import com.developersbreach.designsystem.components.CaVerticalSpacer

@Composable
fun SignUpScreenUI(
    onClickSignUp: () -> Unit,
    onClickNavigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    login: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onPasswordToggledVisibilityChange: () -> Unit,
    onCodeChange: (String) -> Unit,
    onClickConfirm: (String) -> Unit,
    data: SignUpUiState,
    scaffoldState: ScaffoldState,
    error: String?,
) {
    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = Modifier,
    ) {
        CaScaffold(
            modifier = Modifier,
            scaffoldState = scaffoldState,
            topBar = { SignUpTopAppBar(navigateUp = onClickNavigateUp) },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp),
            ) {
                CaTextH5(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 58.sp,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colors.primary,
                    ),
                )
                CaVerticalSpacer(value = 28)
                when (data.signUpStep) {
                    is SignUpStep.Initial -> {
                        InitialStepUI(
                            data = data,
                            onEmailChange = onEmailChange,
                            onPasswordChange = onPasswordChange,
                            onPasswordToggledVisibilityChange = onPasswordToggledVisibilityChange,
                            onConfirmPasswordChange = onConfirmPasswordChange,
                            error = error,
                            onClickSignUp = onClickSignUp,
                        )
                    }

                    is SignUpStep.AwaitingConfirmation -> {
                        AwaitingConfirmationStepUI(
                            code = data.signUpStep.code,
                            onCodeChange = onCodeChange,
                            error = error,
                            onClickConfirm = onClickConfirm,
                        )
                    }

                    is SignUpStep.ConfirmationCompleted -> {
                        login()
                        CaTextH6(
                            text = stringResource(R.string.sign_up_success),
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.primary,
                            textAlign = TextAlign.Start,
                        )
                    }

                    SignUpStep.LoginProcessCompleted -> {
                        navigateToHome()
                    }
                }
            }
        }
    }
}

@Composable
private fun InitialStepUI(
    data: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordToggledVisibilityChange: () -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    error: String?,
    onClickSignUp: () -> Unit,
) {
    CaOutlinedTextField(
        value = data.email,
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
    CaVerticalSpacer(value = 4)
    CaOutlinedTextField(
        value = data.password,
        onValueChange = onPasswordChange,
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
                data.showPassword -> Icons.Rounded.Visibility
                else -> Icons.Rounded.VisibilityOff
            },
        ),
        placeholderText = stringResource(R.string.password),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = when {
            data.showPassword -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
    )
    CaVerticalSpacer(value = 4)
    CaOutlinedTextField(
        value = data.confirmPassword,
        onValueChange = onConfirmPasswordChange,
        leadingIcon = CaTextFieldIconConfig(
            modifier = Modifier.padding(start = 4.dp),
            iconModifier = Modifier,
            imageVector = Icons.Rounded.Password,
            tint = MaterialTheme.colors.onBackground,
        ),
        placeholderText = stringResource(R.string.confirm_password),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = when {
            data.showPassword -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
    )
    CaVerticalSpacer(value = 16)
    if (error != null) {
        CaTextBody1(
            text = error,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colors.error,
        )
    }
    CaButtonFilled(
        title = stringResource(R.string.sign_up),
        onClick = onClickSignUp,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun AwaitingConfirmationStepUI(
    code: String,
    onCodeChange: (String) -> Unit,
    error: String?,
    onClickConfirm: (String) -> Unit,
) {
    CaOutlinedTextField(
        value = code,
        onValueChange = onCodeChange,
        label = stringResource(R.string.confirmation_code),
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = CaTextFieldIconConfig(
            modifier = Modifier.padding(start = 4.dp),
            iconModifier = Modifier,
            imageVector = Icons.Rounded.AppRegistration,
            tint = MaterialTheme.colors.onBackground,
        ),
    )
    CaVerticalSpacer(value = 16)
    if (error != null) {
        CaTextBody1(
            text = error,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colors.error,
        )
    }
    CaButtonFilled(
        title = stringResource(R.string.confirm),
        onClick = { onClickConfirm(code) },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun SignUpScreenPreview(
    signUpStep: SignUpStep,
) {
    SignUpScreenUI(
        onClickSignUp = {},
        onClickNavigateUp = {},
        navigateToHome = {},
        login = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onPasswordToggledVisibilityChange = {},
        onCodeChange = {},
        onClickConfirm = {},
        data = SignUpUiState(
            email = "",
            password = "",
            confirmPassword = "",
            showPassword = false,
            signUpStep = signUpStep,
        ),
        error = null,
        scaffoldState = rememberScaffoldState(),
    )
}

@PreviewLightDark
@Composable
fun SignUpScreenInitialStepPreview() {
    ComposeActorsTheme {
        SignUpScreenPreview(
            signUpStep = SignUpStep.Initial,
        )
    }
}

@PreviewLightDark
@Composable
fun SignUpScreenAwaitingConfirmationStepPreview() {
    ComposeActorsTheme {
        SignUpScreenPreview(
            signUpStep = SignUpStep.AwaitingConfirmation(
                email = "haha.lulu@example.com",
                password = "odio",
                code = "nobis",
            ),
        )
    }
}

@PreviewLightDark
@Composable
fun SignUpScreenCompletedStepPreview() {
    ComposeActorsTheme {
        SignUpScreenPreview(
            signUpStep = SignUpStep.ConfirmationCompleted,
        )
    }
}