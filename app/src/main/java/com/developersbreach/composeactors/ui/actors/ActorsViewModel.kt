package com.developersbreach.composeactors.ui.actors

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.repository.AppRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

/**
 * To manage ui state and data for screen [ActorsScreen].
 */
class ActorsViewModel(
    application: Application,
    private val repository: AppRepository
) : AndroidViewModel(application) {

    // Holds the state for values in ActorsViewState
    var uiState by mutableStateOf(ActorsViewState())
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
        uiState = ActorsViewState(isFetchingActors = true)
        val popularActorsList = repository.getPopularActorsData()
        val trendingActorsList = repository.getTrendingActorsData()
        uiState = ActorsViewState(
            popularActorList = popularActorsList,
            trendingActorList = trendingActorsList,
            isFetchingActors = false
        )
    }

    companion object {

        /**
         * Factory for [ActorsViewModel] to provide with [AppRepository]
         */
        fun provideFactory(
            application: Application,
            repository: AppRepository
        ): ViewModelProvider.AndroidViewModelFactory {
            return object : ViewModelProvider.AndroidViewModelFactory(application) {
                @Suppress("unchecked_cast")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(ActorsViewModel::class.java)) {
                        return ActorsViewModel(application, repository) as T
                    }
                    throw IllegalArgumentException("Cannot create Instance for ActorsViewModel class")
                }
            }
        }
    }
}

/**
 * Models the UI state for the [ActorsScreen] screen.
 */
data class ActorsViewState(
    var popularActorList: List<Actor> = emptyList(),
    var trendingActorList: List<Actor> = emptyList(),
    val isFetchingActors: Boolean = false,
)