package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.UiStateHandler

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToSelectedPerson: (Int) -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = viewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = viewModel.uiEvent,
    ) { searchData ->
        val navigateToSearchBySearchType = when (searchData.searchType) {
            SearchType.People -> navigateToSelectedPerson
            SearchType.Movies -> navigateToSelectedMovie
        }

        val searchHint = when (searchData.searchType) {
            SearchType.People -> stringResource(R.string.hint_search_query_actors)
            SearchType.Movies -> stringResource(R.string.hint_search_query_movies)
        }

        SearchScreenUI(
            navigateUp = navigateUp,
            navigateToSearchBySearchType = navigateToSearchBySearchType,
            searchHint = searchHint,
            onSearchQueryChange = { query -> viewModel.performQuery(query) },
            data = searchData,
            scaffoldState = scaffoldState,
        )
    }
}