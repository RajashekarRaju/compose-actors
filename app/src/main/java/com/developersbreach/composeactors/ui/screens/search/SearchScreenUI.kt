package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.annotations.PreviewLightDark
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.ui.components.ShowSearchProgress
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme
import com.developersbreach.designsystem.components.CaScaffold
import com.developersbreach.designsystem.components.CaSurface

@Composable
fun SearchScreenUI(
    navigateUp: () -> Unit,
    navigateToSearchBySearchType: (Int) -> Unit,
    searchHint: String,
    onSearchQueryChange: (query: String) -> Unit,
    data: SearchUiState,
    scaffoldState: ScaffoldState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val closeKeyboard = {
        keyboardController?.hide()
    }
    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.semantics { testTag = "TestTag:SearchScreen" },
    ) {
        CaScaffold(
            modifier = Modifier,
            scaffoldState = scaffoldState,
            topBar = {
                SearchAppBar(
                    navigateUp = navigateUp,
                    onQueryChange = onSearchQueryChange,
                    searchHint = searchHint,
                    closeKeyboard = closeKeyboard,
                )
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues),
            ) {
                when (data.searchType) {
                    SearchType.People -> {
                        val isLoadingData = !data.isSearchingResults && data.people.isEmpty()
                        ShowSearchProgress(isLoadingData)
                        PersonSearchUI(
                            persons = data.people,
                            navigateToSelectedPerson = navigateToSearchBySearchType,
                            closeKeyboard = closeKeyboard,
                        )
                    }

                    SearchType.Movies -> {
                        val isLoadingData = !data.isSearchingResults && data.movies.isEmpty()
                        ShowSearchProgress(isLoadingData)
                        MovieSearchUI(
                            movieList = data.movies,
                            navigateToSelectedMovie = navigateToSearchBySearchType,
                            closeKeyboard = closeKeyboard,
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun SearchScreenUIPreview() {
    ComposeActorsTheme {
        SearchScreenUI(
            navigateUp = {},
            navigateToSearchBySearchType = {},
            searchHint = stringResource(R.string.hint_search_query_actors),
            onSearchQueryChange = {},
            data = SearchUiState(
                searchType = SearchType.People,
                isSearchingResults = false,
                people = fakePersonsList(),
            ),
            scaffoldState = rememberScaffoldState(),
        )
    }
}