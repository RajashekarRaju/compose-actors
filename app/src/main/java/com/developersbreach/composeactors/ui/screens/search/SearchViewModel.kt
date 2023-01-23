package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.repository.search.SearchRepository
import com.developersbreach.composeactors.utils.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * To manage ui state and data for screen SearchScreen.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    var uiState by mutableStateOf(SearchUIState())
        private set
    var searchType = SearchType.Actors
    /**
     * @param searchQuery user entered query in text field.
     * This function triggers everytime user makes query change.
     */
    fun performQuery(
        searchQuery: String
    ) {
        if(searchType ==  SearchType.Actors) {
            performQueryForActorsList(searchQuery)
        } else {
            performQueryForMoviesList(searchQuery)
        }
    }

    private fun performQueryForMoviesList(searchQuery: String) {
        viewModelScope.launch {
            // Update the values in uiState from all data sources.
            uiState = SearchUIState(isSearchingResults = true)
            val searchData = searchRepository.getSearchableMoviesData(searchQuery)
            uiState = uiState.copy(
                actorList = searchData as ArrayList<Actor>,
                isSearchingResults = false
            )
        }
    }

    private fun performQueryForActorsList(searchQuery: String) {
        viewModelScope.launch {
            // Update the values in uiState from all data sources.
            uiState = SearchUIState(isSearchingResults = true)
            val searchData = searchRepository.getSearchableActorsData(searchQuery)
            uiState = uiState.copy(
                actorList = searchData as ArrayList<Actor>,
                isSearchingResults = false
            )
        }
    }
}
