package com.developersbreach.composeactors.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * To manage ui state and data for screen [SearchScreen].
 */
class SearchViewModel(
    private val repository: AppRepository
) : ViewModel() {

    // Holds the state for values in SearchViewState
    var uiState by mutableStateOf(SearchViewState())
        private set

    /**
     * @param query user entered query in text field.
     * This function triggers everytime user makes query change.
     */
    fun performQuery(
        query: String
    ) {
        viewModelScope.launch {
            // Update the values in uiState from all data sources.
            uiState = SearchViewState(isSearchingResults = true)
            val searchData = repository.getSearchableActorsData(query)
            uiState = uiState.copy(
                actorList = searchData,
                isSearchingResults = false
            )
        }
    }
}

/**
 * Models the UI state for the [SearchScreen] screen.
 */
data class SearchViewState(
    val actorList: List<Actor> = listOf(),
    val isSearchingResults: Boolean = false,
)
