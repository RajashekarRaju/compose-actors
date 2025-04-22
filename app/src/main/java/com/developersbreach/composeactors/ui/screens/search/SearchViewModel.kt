package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.developersbreach.composeactors.data.search.repository.SearchRepository
import com.developersbreach.composeactors.ui.components.UiState
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
) : ViewModel() {

    val searchType: SearchType = savedStateHandle.toRoute<AppDestinations.Search>().searchType

    var uiState: UiState<SearchDataType> by mutableStateOf(UiState.Success(ActorSearch()))
        private set

    /**
     * @param searchQuery user entered query in text field.
     * This function triggers everytime user makes query change.
     */
    fun performQuery(searchQuery: String) {
        viewModelScope.launch {
            when (searchType) {
                SearchType.Persons -> {
                    uiState = UiState.Success(ActorSearch(isSearchingResults = true))
                    uiState = searchRepository.getSearchableActorsData(
                        query = searchQuery,
                    ).fold(
                        ifLeft = { UiState.Error(it) },
                        ifRight = { UiState.Success(ActorSearch(it, false)) },
                    )
                }
                SearchType.Movies -> {
                    uiState = UiState.Success(MovieSearch(isSearchingResults = true))
                    uiState = searchRepository.getSearchableMoviesData(
                        query = searchQuery,
                    ).fold(
                        ifLeft = { UiState.Error(it) },
                        ifRight = { UiState.Success(MovieSearch(it, false)) },
                    )
                }
            }
        }
    }
}