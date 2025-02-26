package com.developersbreach.composeactors.ui.screens.modalSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.screens.home.HomeOptionItems
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaIcon
import com.developersbreach.designsystem.components.CaIconButton
import com.developersbreach.designsystem.components.CaTextH5
import com.developersbreach.designsystem.components.CaTextH6
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OptionsModalSheetContent(
    modalSheetSheet: ModalBottomSheetState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navigateToFavorite:() -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToAbout: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 28.dp, bottom = 32.dp)
            .navigationBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            CaIconButton(
                modifier = Modifier.size(28.dp),
                iconModifier = Modifier,
                onClick = {
                    coroutineScope.launch {
                        modalSheetSheet.hide()
                    }
                },
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.width(20.dp))

            CaTextH5(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(HomeOptionItems.homeOptions) { option ->
                ItemOptionRow(
                    option = option,
                    navigateToFavorite = navigateToFavorite,
                    navigateToSearch = navigateToSearch,
                    navigateToProfile = navigateToProfile,
                    navigateToAbout = navigateToAbout
                )
            }
        }
    }
}

@Composable
private fun ItemOptionRow(
    option: HomeOptionItems,
    navigateToFavorite: () -> Unit,
    navigateToSearch: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToAbout: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100))
            .clickable {
                when (option.id) {
                    1 -> navigateToFavorite()
                    2 -> navigateToSearch()
                    3 -> navigateToProfile()
                    4 -> navigateToAbout()
                }
            }
            .padding(top = 8.dp, start = 20.dp, end = 20.dp, bottom = 8.dp)
    ) {
        CaIcon(
            painter = painterResource(id = option.icon),
            contentDescription = "",
            tint = MaterialTheme.colors.onBackground.copy(alpha = 0.75f),
            modifier = Modifier
        )

        Spacer(modifier = Modifier.width(24.dp))

        CaTextH6(
            text = option.title,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.75f),
            modifier = Modifier
        )
    }
}

@PreviewLightDark
@Composable
fun OptionsModalSheetContentPreview() {
    ComposeActorsTheme {
        OptionsModalSheetContent(
            navigateToFavorite = {},
            navigateToSearch = {},
            navigateToProfile = {},
            navigateToAbout = {},
            modalSheetSheet = ModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Expanded,
                density = LocalDensity.current
            ),
        )
    }
}