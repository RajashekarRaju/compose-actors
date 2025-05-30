package com.developersbreach.composeactors.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaIconButton
import com.developersbreach.designsystem.components.CaTextH6

@Composable
fun ProfileTopAppBar(
    navigateUp: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
    ) {
        CaIconButton(
            onClick = navigateUp,
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterStart),
            iconModifier = Modifier,
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = stringResource(id = R.string.cd_up_button),
        )
        CaTextH6(
            text = stringResource(R.string.profile),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@PreviewLightDark
@Composable
private fun ProfileTopAppBarPreview() {
    ComposeActorsTheme {
        ProfileTopAppBar { }
    }
}