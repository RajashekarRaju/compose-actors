package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.screens.search.SearchType
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaIcon
import com.developersbreach.designsystem.components.CaTextSubtitle1

/**
 * Appbar contains [HomeTopAppBarContent] which does not perform search query directly.
 * Instead navigates to search screen to submit query.
 * @param navigateToSearch navigates user to search screen.
 */
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    navigateToSearch: (SearchType) -> Unit,
    searchType: SearchType
) {
    TopAppBar(
        content = { HomeTopAppBarContent(navigateToSearch, searchType) },
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = modifier
            .statusBarsPadding()
            .padding(top = 4.dp, start = 16.dp, end = 16.dp)
    )
}

/**
 * AppBar for [HomeScreen]
 */
@Composable
private fun HomeTopAppBarContent(
    navigateToSearch: (SearchType) -> Unit,
    searchType: SearchType
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable { navigateToSearch(searchType) }
            .background(color = MaterialTheme.colors.surface)
    ) {
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        CaIcon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.cd_search_icon),
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier.alpha(0.5f)
        )

        Spacer(modifier = Modifier.padding(horizontal = 12.dp))

        CaTextSubtitle1(
            text = stringResource(R.string.search_app_bar_title),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    ComposeActorsTheme(darkTheme = true) {
        HomeTopAppBarContent(
            navigateToSearch = { },
            searchType = SearchType.Persons
        )
    }
}
