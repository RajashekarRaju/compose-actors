package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaButtonFilled
import com.developersbreach.designsystem.components.CaImage
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaSurface
import com.developersbreach.designsystem.components.CaTextBody1
import com.developersbreach.designsystem.components.CaVerticalSpacer

@Composable
fun ProfileScreenUI(
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit,
    onClickLogout: () -> Unit,
    profileUiState: ProfileUIState,
) {
    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = Modifier,
    ) {
        CaScaffold(
            modifier = Modifier,
            topBar = { ProfileTopAppBar(navigateUp = navigateUp) },
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(80.dp)
                    .padding(paddingValues = paddingValues),
            ) {
                CaImage(
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = null,
                    modifier = Modifier.size(96.dp),
                )
                CaVerticalSpacer(10)
                when (profileUiState) {
                    ProfileUIState.NavigateToLogin -> navigateToLogin()
                    ProfileUIState.GuestUI -> ProfileGuestUI()
                    ProfileUIState.UnauthenticatedUI -> ProfileUnauthenticatedUI(navigateToLogin)
                    is ProfileUIState.AuthenticatedUI -> ProfileAuthenticatedUI(profileUiState, onClickLogout)
                }
            }
        }
    }
}

@Composable
private fun ProfileGuestUI() {
    CaTextBody1(
        text = stringResource(R.string.logged_in_as_guest),
        modifier = Modifier,
        style = TextStyle(
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        ),
    )
}

@Composable
private fun ProfileUnauthenticatedUI(
    navigateToLogin: () -> Unit,
) {
    CaTextBody1(
        text = stringResource(R.string.please_login_to_view_account),
        modifier = Modifier,
        style = TextStyle(
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        ),
    )
    CaVerticalSpacer(10)
    CaButtonFilled(
        title = stringResource(R.string.login),
        onClick = navigateToLogin,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun ProfileAuthenticatedUI(
    profileUiState: ProfileUIState.AuthenticatedUI,
    onClickLogout: () -> Unit,
) {
    CaTextBody1(
        text = stringResource(R.string.welcome, profileUiState.name),
        modifier = Modifier,
        style = TextStyle(
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        ),
    )
    CaVerticalSpacer(10)
    CaButtonFilled(
        title = stringResource(R.string.logout),
        onClick = onClickLogout,
        modifier = Modifier.fillMaxWidth(),
    )
}

@PreviewLightDark
@Composable
fun ProfileScreenAuthenticatedPreview() {
    ComposeActorsTheme {
        ProfileScreenUI(
            navigateUp = { },
            onClickLogout = { },
            navigateToLogin = { },
            profileUiState = ProfileUIState.AuthenticatedUI("Raj"),
        )
    }
}

@PreviewLightDark
@Composable
fun ProfileScreenUnauthenticatedPreview() {
    ComposeActorsTheme {
        ProfileScreenUI(
            navigateUp = { },
            onClickLogout = { },
            navigateToLogin = { },
            profileUiState = ProfileUIState.UnauthenticatedUI,
        )
    }
}

@PreviewLightDark
@Composable
fun ProfileScreenGuestPreview() {
    ComposeActorsTheme {
        ProfileScreenUI(
            navigateUp = { },
            onClickLogout = { },
            navigateToLogin = { },
            profileUiState = ProfileUIState.GuestUI,
        )
    }
}