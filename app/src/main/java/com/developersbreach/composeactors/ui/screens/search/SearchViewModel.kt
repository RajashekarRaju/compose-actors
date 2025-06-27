package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.developersbreach.composeactors.data.search.repository.SearchRepository
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiEvent
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
import com.developersbreach.composeactors.ui.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * To manage ui state and data for screen SearchScreen.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository,
) : BaseViewModel() {

    private val searchType: SearchType = savedStateHandle.toRoute<AppDestinations.Search>().searchType

    var uiState: UiState<SearchUiState> by mutableStateOf(UiState.Success(SearchUiState(searchType)))
        private set

    fun performQuery(
        searchQuery: String,
    ) {
        viewModelScope.launch {
            uiState = uiState.modifyLoadedState {
                copy(isSearchingResults = true)
            }
            uiState = when (searchType) {
                SearchType.People -> {
                    searchRepository.getSearchableActorsData(
                        query = searchQuery,
                    ).fold(
                        ifLeft = { UiState.Error(it) },
                        ifRight = { result ->
                            if (result.isEmpty()) {
                                sendUiEvent(UiEvent.ShowMessage("No people found for your search"))
                            }
                            uiState.modifyLoadedState {
                                copy(
                                    isSearchingResults = false,
                                    people = result,
                                )
                            }
                        },
                    )
                }
                SearchType.Movies -> {
                    searchRepository.getSearchableMoviesData(
                        query = searchQuery,
                    ).fold(
                        ifLeft = { UiState.Error(it) },
                        ifRight = { result ->
                            if (result.isEmpty()) {
                                sendUiEvent(UiEvent.ShowMessage("No movies found for your search"))
                            }
                            uiState.modifyLoadedState {
                                copy(
                                    isSearchingResults = false,
                                    movies = result,
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}