package com.developersbreach.composeactors.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.composeactors.ui.details.DetailScreen
import com.developersbreach.composeactors.ui.actors.ActorsScreen

/**
 * @param navigateUp navigates back to previous screen.
 * @param title actor name in center of app bar.
 * AppBar for [DetailScreen]
 */
@Composable
fun DetailAppBar(
    navigateUp: () -> Unit,
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
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
            text = title,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center)
        )
    }
}

/**
 * AppBar for [ActorsScreen]
 */
@Composable
fun SearchBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 8.dp, start = 4.dp, end = 16.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.cd_search_icon),
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.alpha(0.5f)
        )

        Spacer(modifier = Modifier.padding(horizontal = 12.dp))

        Text(
            text = stringResource(R.string.search_app_bar_title),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    ComposeActorsTheme(darkTheme = true) {
        SearchBar()
    }
}
