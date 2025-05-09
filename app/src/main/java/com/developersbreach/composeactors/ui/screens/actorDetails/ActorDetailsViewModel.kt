package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.raise.either
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.model.toWatchlistPerson
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val personId: Int = savedStateHandle.toRoute<AppDestinations.ActorDetail>().personId

    var detailUIState: UiState<ActorDetailsData> by mutableStateOf(UiState.Loading)
        private set

    var sheetUIState by mutableStateOf(ActorDetailsSheetUIState())
        private set

    val isPersonInWatchlist: LiveData<Int> = personRepository.isPersonInWatchlist(personId)

    init {
        viewModelScope.launch {
            startFetchingDetails()
        }
    }

    private suspend fun startFetchingDetails() {
        detailUIState = UiState.Success(ActorDetailsData(isFetchingDetails = true))
        detailUIState = either {
            ActorDetailsData(
                castList = personRepository.getCastDetails(personId).bind(),
                actorData = personRepository.getPersonDetails(personId).bind(),
                isFetchingDetails = false,
            )
        }.fold(
            ifLeft = { UiState.Error(it) },
            ifRight = { UiState.Success(it) },
        )
    }

    fun getSelectedMovieDetails(
        movieId: Int?,
    ) {
        if (movieId == null) {
            Timber.e("Failed to getSelectedMovieDetails, since id was null")
            return
        }
        viewModelScope.launch {
            movieRepository.getMovieDetails(
                movieId = movieId,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { sheetUIState = ActorDetailsSheetUIState(selectedMovieDetails = it) },
            )
        }
    }

    fun addActorToWatchlist(
        actor: PersonDetail?,
    ) {
        if (actor == null) {
            Timber.e("Actor was null while adding to watchlist operation.")
            return
        }
        viewModelScope.launch {
            personRepository.addPersonToWatchlist(actor.toWatchlistPerson())
        }
    }

    fun removeActorFromWatchlist(actor: PersonDetail?) {
        if (actor == null) {
            Timber.e("Actor was null while delete operation.")
            return
        }
        viewModelScope.launch {
            personRepository.deleteSelectedPersonFromWatchlist(
                actor.toWatchlistPerson(),
            )
        }
    }
}