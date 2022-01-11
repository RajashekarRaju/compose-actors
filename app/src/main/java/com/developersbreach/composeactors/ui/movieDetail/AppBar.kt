package com.developersbreach.composeactors.ui.movieDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.developersbreach.composeactors.R

/**
 * @param navigateUp navigates back to previous screen.
 * @param title actor name in center of app bar.
 * AppBar for [MovieDetailScreen]
 */
@Composable
fun MovieDetailAppBar(
    navigateUp: () -> Unit,
    title: String?
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = navigateUp,
            modifier = Modifier.align(alignment = Alignment.CenterStart)
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
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center)
        )
    }
}
