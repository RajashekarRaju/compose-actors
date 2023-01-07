package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.repository.movie.MovieRepository
import com.developersbreach.composeactors.data.repository.search.SearchRepository
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

    /**
     * @param searchQuery user entered query in text field.
     * This function triggers everytime user makes query change.
     */
    fun performQuery(
        searchQuery: String
    ) {
        viewModelScope.launch {
            // Update the values in uiState from all data sources.
            uiState = SearchUIState(isSearchingResults = true)
            val searchData = searchRepository.getSearchableActorsData(searchQuery)
            uiState = uiState.copy(
                actorList = searchData,
                isSearchingResults = false
            )
        }
    }
}
