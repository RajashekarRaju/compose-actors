package com.developersbreach.composeactors.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaButtonFilled
import com.developersbreach.designsystem.components.CaButtonText
import com.developersbreach.designsystem.components.CaImage
import com.developersbreach.designsystem.components.CaOutlinedTextField
import com.developersbreach.designsystem.components.CaTextFieldIconConfig
import com.developersbreach.designsystem.components.CaVerticalSpacer

@Composable
fun LoginScreenUI(
    onClickLogin: () -> Unit,
    onClickSkip: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordToggledVisibilityChange: () -> Unit,
    data: LoginData,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
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
                modifier = Modifier.shadow(elevation = 40.dp),
            )

            CaVerticalSpacer(value = 28)

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

            CaVerticalSpacer(value = 16)

            CaButtonFilled(
                title = stringResource(R.string.login),
                onClick = onClickLogin,
                modifier = Modifier.fillMaxWidth(),
            )

            CaButtonText(
                title = stringResource(R.string.skip),
                onClick = onClickSkip,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        }
    }
}

@PreviewLightDark
@Composable
fun LoginScreenPreview() {
    ComposeActorsTheme {
        LoginScreenUI(
            onClickLogin = {},
            onClickSkip = {},
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordToggledVisibilityChange = {},
            data = LoginData(
                email = "someone@google.com",
                password = "bad_password",
                showPassword = true,
            ),
        )
    }
}