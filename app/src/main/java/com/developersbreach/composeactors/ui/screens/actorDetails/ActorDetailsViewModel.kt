package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.PersonDetail
import com.developersbreach.composeactors.data.model.toFavoritePerson
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.ui.navigation.AppDestinations.ACTOR_DETAIL_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * To manage ui state and data for screen ActorDetailsScreen.
 */
@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
) : ViewModel() {

    private val personId: Int = checkNotNull(savedStateHandle[ACTOR_DETAIL_ID_KEY])

    var detailUIState by mutableStateOf(ActorDetailsUIState(actorData = null))
        private set

    var sheetUIState by mutableStateOf(ActorDetailsSheetUIState())
        private set

    val isFavoriteMovie: LiveData<Int> = personRepository.isFavoritePerson(personId)

    init {
        viewModelScope.launch {
            try {
                startFetchingDetails()
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    private suspend fun startFetchingDetails() {
        detailUIState = ActorDetailsUIState(isFetchingDetails = true, actorData = null)
        val actorData = personRepository.getPersonDetails(personId)
        val castData = personRepository.getCastDetails(personId)
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
                    val movieData = movieRepository.getMovieDetails(id)
                    sheetUIState = ActorDetailsSheetUIState(selectedMovieDetails = movieData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    fun addActorToFavorites() {
        viewModelScope.launch {
            val actor: PersonDetail? = detailUIState.actorData
            if (actor != null) {
                personRepository.addPersonToFavorite(
                    actor.toFavoritePerson()
                )
            } else {
                Timber.e("Id of ${actor} was null while adding to favorite operation.")
            }
        }
    }

    fun removeActorFromFavorites() {
        viewModelScope.launch {
            val actor: PersonDetail? = detailUIState.actorData
            if (actor != null) {
                personRepository.deleteSelectedFavoritePerson(
                    actor.toFavoritePerson()
                )
            } else {
                Timber.e("Id of ${actor} was null while delete operation.")
            }
        }
    }
}