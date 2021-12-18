package com.developersbreach.composeactors.ui.search

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.repository.AppRepository
import kotlinx.coroutines.launch

/**
 * To manage ui state and data for screen [SearchScreen].
 */
class SearchViewModel(
    application: Application,
    private val repository: AppRepository
) : AndroidViewModel(application) {

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

    companion object {

        /**
         * Factory for [SearchViewModel] to provide with [AppRepository]
         */
        fun provideFactory(
            application: Application,
            repository: AppRepository
        ): ViewModelProvider.AndroidViewModelFactory {
            return object : ViewModelProvider.AndroidViewModelFactory(application) {
                @Suppress("unchecked_cast")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                        return SearchViewModel(application, repository) as T
                    }
                    throw IllegalArgumentException("Cannot create Instance for SearchViewModel class")
                }
            }
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
