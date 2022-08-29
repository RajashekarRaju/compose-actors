package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R

/**
 * @param navigateUp navigates back to previous screen.
 * @param title actor name in center of app bar.
 * AppBar for [MovieDetailScreen]
 */
@Composable
fun MovieDetailTopAppBar(
    navigateUp: () -> Unit,
    title: String?,
    showTopBarBackground: MutableState<Boolean>
) {
    val modifier = if (showTopBarBackground.value) {
        Modifier.background(color = MaterialTheme.colors.background)
    } else {
        Modifier
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = navigateUp,
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = stringResource(id = R.string.cd_up_button)
            )
        }

        Text(
            text = "$title",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp)
        )
    }
}
