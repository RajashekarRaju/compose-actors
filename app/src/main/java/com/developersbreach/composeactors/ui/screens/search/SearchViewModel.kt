package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.repository.search.SearchRepository
import com.developersbreach.composeactors.ui.navigation.AppDestinations.SEARCH_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

/**
 * To manage ui state and data for screen SearchScreen.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val searchRepository: SearchRepository
) : ViewModel() {

    val searchType: SearchType = checkNotNull(savedStateHandle[SEARCH_TYPE])

    var uiState: SearchUIState by mutableStateOf(SearchUIState.ActorSearch())
        private set

    /**
     * @param searchQuery user entered query in text field.
     * This function triggers everytime user makes query change.
     */
    fun performQuery(searchQuery: String) {
        viewModelScope.launch {
            when (searchType) {
                SearchType.Actors -> {
                    uiState = SearchUIState.ActorSearch(isSearchingResults = true)
                    val searchData = searchRepository.getSearchableActorsData(searchQuery)
                    uiState = (uiState as SearchUIState.ActorSearch).copy(
                        actorList = searchData,
                        isSearchingResults = false
                    )
                }
                SearchType.Movies -> {
                    uiState = SearchUIState.MovieSearch(isSearchingResults = true)
                    val searchData = searchRepository.getSearchableMoviesData(searchQuery)
                    uiState = (uiState as SearchUIState.MovieSearch).copy(
                        movieList = searchData,
                        isSearchingResults = false
                    )
                }
            }
        }
    }
}
