package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.datasource.fake.fakeActorsList
import com.developersbreach.composeactors.ui.components.ShowSearchProgress
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreenUI(
    navigateUp: () -> Unit,
    navigateToSearchBySearchType: (Int) -> Unit,
    searchHint: String,
    onSearchQueryChange: (query: String) -> Unit,
    uiState: SearchUIState,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val closeKeyboard = {
        keyboardController?.hide()
    }

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.semantics { testTag = "TestTag:SearchScreen" }
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(
                    navigateUp = navigateUp,
                    onQueryChange = onSearchQueryChange,
                    searchHint = searchHint,
                    closeKeyboard = closeKeyboard
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                when (uiState) {
                    is SearchUIState.ActorSearch -> {
                        // Show progress while search is happening
                        val isLoadingData = !uiState.isSearchingResults && uiState.actorList.isEmpty()
                        ShowSearchProgress(isLoadingData)
                        // Main content for this screen
                        ActorSearchUI(
                            actorsList = uiState.actorList,
                            navigateToSelectedActor = navigateToSearchBySearchType,
                            closeKeyboard = closeKeyboard
                        )
                    }
                    is SearchUIState.MovieSearch -> {
                        // Show progress while search is happening
                        val isLoadingData = !uiState.isSearchingResults && uiState.movieList.isEmpty()
                        ShowSearchProgress(isLoadingData)
                        // Main content for this screen
                        MovieSearchUI(
                            movieList = uiState.movieList,
                            navigateToSelectedMovie = navigateToSearchBySearchType,
                            closeKeyboard = closeKeyboard
                        )
                    }
                }
            }
        }
    }
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
            uiState = SearchUIState.ActorSearch(
                actorList = fakeActorsList(),
                isSearchingResults = false
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenUILightPreview() {
    ComposeActorsTheme(darkTheme = false) {
        SearchScreenUI(
            navigateUp = {},
            navigateToSearchBySearchType = {},
            searchHint = stringResource(R.string.hint_search_query_actors),
            onSearchQueryChange = {},
            uiState = SearchUIState.ActorSearch(
                actorList = fakeActorsList(),
                isSearchingResults = false
            )
        )
    }
}