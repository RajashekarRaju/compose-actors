package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
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
import com.developersbreach.composeactors.data.region.model.Region
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaButtonFilled
import com.developersbreach.composeactors.ui.components.CaDropdown
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
    profileUiState: ProfileUiState,
    scaffoldState: ScaffoldState,
    onRegionSelected: (Region) -> Unit,
    onRegionDropdownToggle: () -> Unit,
) {
    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = Modifier,
    ) {
        CaScaffold(
            modifier = Modifier,
            topBar = { ProfileTopAppBar(navigateUp = navigateUp) },
            scaffoldState = scaffoldState,
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
                when (profileUiState.actions) {
                    ProfileActions.NavigateToLogin -> navigateToLogin()
                    ProfileActions.GuestUI -> ProfileGuestUI(navigateToLogin)
                    is ProfileActions.AuthenticatedUI -> ProfileAuthenticatedUI(profileUiState.actions, onClickLogout)
                    ProfileActions.UnauthenticatedUI, null -> ProfileUnauthenticatedUI(navigateToLogin)
                }
                CaVerticalSpacer(20)
                RegionDropdown(
                    profileUiState = profileUiState,
                    onRegionSelected = onRegionSelected,
                    onRegionDropdownToggle = onRegionDropdownToggle,
                )
            }
        }
    }
}

@Composable
private fun ProfileGuestUI(
    navigateToLogin: () -> Unit,
) {
    CaTextBody1(
        text = stringResource(R.string.logged_in_as_guest),
        modifier = Modifier,
        style = TextStyle(
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
        ),
    )
    CaVerticalSpacer(4)
    ProfileUnauthenticatedUI(navigateToLogin)
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
    profileActions: ProfileActions.AuthenticatedUI,
    onClickLogout: () -> Unit,
) {
    CaTextBody1(
        text = stringResource(R.string.welcome, profileActions.name),
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

@Composable
private fun RegionDropdown(
    profileUiState: ProfileUiState,
    onRegionSelected: (Region) -> Unit,
    onRegionDropdownToggle: () -> Unit,
) {
    CaDropdown(
        modifier = Modifier,
        items = profileUiState.regions,
        selectedItem = profileUiState.region,
        isDropdownExpanded = profileUiState.isDropdownExpanded,
        onExpanded = onRegionDropdownToggle,
        onItemClick = onRegionSelected,
        itemText = { it.name },
        defaultSelectionTitle = stringResource(R.string.select_region),
    )
}

@Composable
private fun ProfileScreenPreview(
    profileUiState: ProfileUiState,
) {
    ComposeActorsTheme {
        ProfileScreenUI(
            navigateUp = { },
            onClickLogout = { },
            navigateToLogin = { },
            profileUiState = profileUiState,
            scaffoldState = rememberScaffoldState(),
            onRegionSelected = {},
            onRegionDropdownToggle = {},
        )
    }
}

@PreviewLightDark
@Composable
fun ProfileScreenAuthenticatedPreview() {
    ProfileScreenPreview(ProfileUiState(actions = ProfileActions.AuthenticatedUI("Raj")))
}

@PreviewLightDark
@Composable
fun ProfileScreenUnauthenticatedPreview() {
    ProfileScreenPreview(ProfileUiState(actions = ProfileActions.UnauthenticatedUI))
}

@PreviewLightDark
@Composable
fun ProfileScreenGuestPreview() {
    ProfileScreenPreview(ProfileUiState(actions = ProfileActions.GuestUI))
}