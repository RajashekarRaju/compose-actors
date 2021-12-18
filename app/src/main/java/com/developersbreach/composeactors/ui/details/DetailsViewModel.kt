package com.developersbreach.composeactors.ui.details

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.repository.AppRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

/**
 * To manage ui state and data for screen [DetailScreen].
 */
class DetailsViewModel(
    application: Application,
    private val actorId: Int,
    private val repository: AppRepository
) : AndroidViewModel(application) {

    // Holds the state for values in DetailsViewState
    var uiState by mutableStateOf(DetailsViewState(actorData = null))
        private set

    init {
        viewModelScope.launch {
            try {
                startFetchingDetails()
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    // Update the values in uiState from all data sources.
    private suspend fun startFetchingDetails() {
        uiState = DetailsViewState(isFetchingDetails = true, actorData = null)
        val actorData = repository.getSelectedActorData(actorId)
        val castData = repository.getCastData(actorId)
        uiState = DetailsViewState(
            castList = castData,
            actorData = actorData,
            isFetchingDetails = false
        )
    }

    companion object {

        /**
         * Factory for [DetailsViewModel] to provide with [AppRepository]
         */
        fun provideFactory(
            application: Application,
            actorId: Int,
            repository: AppRepository
        ): ViewModelProvider.AndroidViewModelFactory {
            return object : ViewModelProvider.AndroidViewModelFactory(application) {
                @Suppress("unchecked_cast")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                        return DetailsViewModel(application, actorId, repository) as T
                    }
                    throw IllegalArgumentException("Cannot create Instance for DetailsViewModel class")
                }
            }
        }
    }
}

/**
 * Models the UI state for the [DetailScreen] screen.
 */
data class DetailsViewState(
    val castList: List<Movie> = listOf(),
    val actorData: ActorDetail?,
    val isFetchingDetails: Boolean = false,
)