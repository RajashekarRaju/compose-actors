package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.designsystem.components.CaIconButton
import com.developersbreach.designsystem.components.CaTextH6

/**
 * @param navigateUp navigates back to previous screen.
 * @param title actor name in center of app bar.
 * AppBar for [MovieDetailScreen]
 */
@Composable
fun MovieDetailTopAppBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    title: String?,
    showTopBarBackground: State<Boolean>
) {
    val conditionalModifier = if (showTopBarBackground.value) {
        modifier.background(color = MaterialTheme.colors.background)
    } else {
        modifier
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = conditionalModifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        CaIconButton(
            onClick = navigateUp,
            modifier = Modifier.padding(start = 4.dp),
            iconModifier = Modifier,
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = stringResource(id = R.string.cd_up_button)
        )
        CaTextH6(
            text = "$title",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp)
        )
    }
}
