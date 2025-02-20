package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.R
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
    data: SearchDataType,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val closeKeyboard = {
        keyboardController?.hide()
    }
    CaSurface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.semantics { testTag = "TestTag:SearchScreen" },
        content = {
            CaScaffold(
                modifier = Modifier,
                topBar = {
                    SearchAppBar(
                        navigateUp = navigateUp,
                        onQueryChange = onSearchQueryChange,
                        searchHint = searchHint,
                        closeKeyboard = closeKeyboard
                    )
                },
                content = {paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        when (data) {
                            is ActorSearch -> {
                                // Show progress while search is happening
                                val isLoadingData = !data.isSearchingResults && data.personList.isEmpty()
                                ShowSearchProgress(isLoadingData)
                                // Main content for this screen
                                PersonSearchUI(
                                    persons = data.personList,
                                    navigateToSelectedPerson = navigateToSearchBySearchType,
                                    closeKeyboard = closeKeyboard
                                )
                            }
                            is MovieSearch -> {
                                // Show progress while search is happening
                                val isLoadingData = !data.isSearchingResults && data.movieList.isEmpty()
                                ShowSearchProgress(isLoadingData)
                                // Main content for this screen
                                MovieSearchUI(
                                    movieList = data.movieList,
                                    navigateToSelectedMovie = navigateToSearchBySearchType,
                                    closeKeyboard = closeKeyboard
                                )
                            }
                        }
                    }

                }
            )
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF211a18)
@Composable
private fun SearchScreenUIDarkPreview() {
    ComposeActorsTheme(darkTheme = true) {
        SearchScreenUI(
            navigateUp = {},
            navigateToSearchBySearchType = {},
            searchHint = stringResource(R.string.hint_search_query_actors),
            onSearchQueryChange = {},
            data = ActorSearch(
                personList = fakePersonsList(),
                isSearchingResults = false
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        SearchScreenUI(
            navigateUp = {},
            navigateToSearchBySearchType = {},
            searchHint = stringResource(R.string.hint_search_query_actors),
            onSearchQueryChange = {},
            data = ActorSearch(
                personList = fakePersonsList(),
                isSearchingResults = false
            )
        )
    }
}