package com.developersbreach.composeactors.ui.actorDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.model.MovieDetail
import com.developersbreach.composeactors.repository.AppRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

/**
 * To manage ui state and data for screen [DetailScreen].
 */
class ActorDetailsViewModel(
    private val actorId: Int,
    private val repository: AppRepository
) : ViewModel() {

    // Holds the state for values in DetailsViewState
    var uiState by mutableStateOf(DetailsUiState(actorData = null))
        private set

    // Holds the state for values in SheetUiState, this state is valid only for modal sheet.
    var sheetUiState by mutableStateOf(SheetUiState())
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
        uiState = DetailsUiState(isFetchingDetails = true, actorData = null)
        val actorData = repository.getSelectedActorData(actorId)
        val castData = repository.getCastData(actorId)
        uiState = DetailsUiState(
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
                if (movieId != null) {
                    val movieData = repository.getSelectedMovieData(movieId)
                    sheetUiState = SheetUiState(selectedMovieDetails = movieData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }
}

/**
 * Models the UI state for the [DetailScreen] screen.
 */
data class DetailsUiState(
    val castList: List<Movie> = listOf(),
    val actorData: ActorDetail? = null,
    val isFetchingDetails: Boolean = false,
)

/**
 * Models the UI state for the [SheetContent] modal sheet.
 */
data class SheetUiState(
    val selectedMovieDetails: MovieDetail? = null
)