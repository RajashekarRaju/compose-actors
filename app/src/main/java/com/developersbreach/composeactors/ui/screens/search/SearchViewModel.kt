package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.developersbreach.composeactors.data.search.repository.SearchRepository
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.ui.components.MessageDuration
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
import com.developersbreach.composeactors.ui.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * To manage ui state and data for screen SearchScreen.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    private val searchType: SearchType = savedStateHandle.toRoute<AppDestinations.Search>().searchType

    var uiState: UiState<SearchUiState> by mutableStateOf(UiState.Success(SearchUiState(searchType)))
        private set

    fun performQuery(
        searchQuery: String,
    ) {
        uiState = uiState.modifyLoadedState {
            copy(isSearchingResults = true)
        }

        when (searchType) {
            SearchType.People -> executeEither(
                action = { searchRepository.getSearchableActorsData(searchQuery) },
                onSuccess = { results ->
                    uiState = uiState.modifyLoadedState {
                        copy(
                            isSearchingResults = false,
                            people = results,
                        )
                    }
                    if (results.isEmpty()) {
                        showMessage("No results found")
                    }
                },
            )

            SearchType.Movies -> executeEither(
                action = { searchRepository.getSearchableMoviesData(searchQuery) },
                onSuccess = { results ->
                    uiState = uiState.modifyLoadedState {
                        copy(
                            isSearchingResults = false,
                            movies = results,
                        )
                    }
                    if (results.isEmpty()) {
                        showMessage(
                            message = "No results found",
                            duration = MessageDuration.LONG,
                        )
                    }
                },
            )
        }
    }
}