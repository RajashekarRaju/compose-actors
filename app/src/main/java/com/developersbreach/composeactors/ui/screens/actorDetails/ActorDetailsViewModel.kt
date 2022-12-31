package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.repository.NetworkRepository
import com.developersbreach.composeactors.ui.navigation.AppDestinations.ACTOR_DETAIL_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * To manage ui state and data for screen [ActorDetailsScreen].
 */
@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: NetworkRepository
) : ViewModel() {

    private val actorId: Int = checkNotNull(savedStateHandle[ACTOR_DETAIL_ID_KEY])

    // Holds the state for values in DetailsViewState
    var detailUIState by mutableStateOf(ActorDetailsUIState(actorData = null))
        private set

    // Holds the state for values in SheetUiState, this state is valid only for modal sheet.
    var sheetUIState by mutableStateOf(ActorDetailsSheetUIState())
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
        detailUIState = ActorDetailsUIState(isFetchingDetails = true, actorData = null)
        val actorData = repository.getSelectedActorData(actorId)
        val castData = repository.getCastData(actorId)
        detailUIState = ActorDetailsUIState(
            castList = castData,
            actorData = actorData,
            isFetchingDetails = false
        )
    }

    /**
     * @param movieId for querying selected movie details.
     * This function will be triggered only when user clicks any movie items.
     * Updates the data values to show in modal sheet.
     */
    fun getSelectedMovieDetails(
        movieId: Int?
    ) {
        viewModelScope.launch {
            try {
                movieId?.let { id ->
                    val movieData = repository.getSelectedMovieData(id)
                    sheetUIState = ActorDetailsSheetUIState(selectedMovieDetails = movieData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }
}