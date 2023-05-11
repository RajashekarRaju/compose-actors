package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.screens.home.HomeScreen

/**
 * @param viewModel to manage ui state of [SearchScreen]
 * @param navigateUp navigates user to previous screen.
 *
 * This destination can be accessed only from [HomeScreen].
 * Shows searchable category list of actors in row.
 * Shows [SearchAppBar] search box looking ui in [TopAppBar]
 */
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToSelectedActor: (Int) -> Unit,
    navigateToSelectedMovie: (Int) -> Unit
) {
    val uiState = viewModel.uiState

    val navigateToSearchBySearchType = when (viewModel.searchType) {
        SearchType.Actors -> navigateToSelectedActor
        SearchType.Movies -> navigateToSelectedMovie
    }

    val searchHint = when (viewModel.searchType) {
        SearchType.Actors -> stringResource(R.string.hint_search_query_actors)
        SearchType.Movies -> stringResource(R.string.hint_search_query_movies)
    }

    SearchScreenUI(
        navigateUp = navigateUp,
        navigateToSearchBySearchType = navigateToSearchBySearchType,
        searchHint = searchHint,
        onSearchQueryChange = { query -> viewModel.performQuery(query) },
        uiState = uiState
    )
}