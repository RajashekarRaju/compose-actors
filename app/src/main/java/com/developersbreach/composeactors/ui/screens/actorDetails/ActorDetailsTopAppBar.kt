package com.developersbreach.composeactors.ui.screens.actorDetails

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.designsystem.components.CaIconButton
import com.developersbreach.designsystem.components.CaTextH6

/**
 * @param navigateUp navigates back to previous screen.
 * @param title actor name in center of app bar.
 * AppBar for [ActorDetailsScreen]
 */
@Composable
internal fun ActorDetailsTopAppBar(
    navigateUp: () -> Unit,
    title: String,
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .padding(start = 4.dp),
    ) {
        CaIconButton(
            onClick = navigateUp,
            modifier = Modifier.align(alignment = Alignment.CenterStart),
            iconModifier = Modifier,
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = stringResource(id = R.string.cd_up_button),
        )
        CaTextH6(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center),
        )
    }
}