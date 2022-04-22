package com.developersbreach.composeactors.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.repository.network.NetworkRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

/**
 * To manage ui state and data for screen [HomeScreen].
 */
class HomeViewModel(
    private val repository: NetworkRepository
) : ViewModel() {

    // Holds the state for values in HomeViewState
    var uiState by mutableStateOf(HomeViewState())
        private set

    init {
        viewModelScope.launch {
            try {
                startFetchingActors()
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    // Update the values in uiState from all data sources.
    private suspend fun startFetchingActors() {
        uiState = HomeViewState(isFetchingActors = true)
        val popularActorsList = repository.getPopularActorsData()
        val trendingActorsList = repository.getTrendingActorsData()
        uiState = HomeViewState(
            popularActorList = popularActorsList,
            trendingActorList = trendingActorsList,
            isFetchingActors = false
        )
    }
}

/**
 * Models the UI state for the [HomeScreen] screen.
 */
data class HomeViewState(
    var popularActorList: List<Actor> = emptyList(),
    var trendingActorList: List<Actor> = emptyList(),
    val isFetchingActors: Boolean = false,
)