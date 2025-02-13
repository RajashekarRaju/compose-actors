package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaIconButton

@Composable
fun FavoritesTopAppBar(
    navigateUp: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        CaIconButton(
            onClick = navigateUp,
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterStart),
            imageVector = Icons.Rounded.ArrowBack,
            tint = MaterialTheme.colors.onBackground,
            contentDescription = stringResource(id = R.string.cd_up_button)
        )

        Text(
            text = "Favorites",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesTopAppBarLightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        FavoritesTopAppBar { }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun FavoritesTopAppBarDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        FavoritesTopAppBar { }
    }
}