package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.raise.either
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiEvent
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
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
    private val watchlistRepository: WatchlistRepository,
) : BaseViewModel() {

    private val personId: Int = savedStateHandle.toRoute<AppDestinations.ActorDetail>().personId

    var detailUIState: UiState<ActorDetailsUiState> by mutableStateOf(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            startFetchingDetails()
        }
    }

    private suspend fun startFetchingDetails() {
        detailUIState = UiState.Success(ActorDetailsUiState(isFetchingDetails = true))
        detailUIState = either {
            ActorDetailsUiState(
                castList = personRepository.getCastDetails(personId).bind(),
                actorData = personRepository.getPersonDetails(personId).bind(),
                isPersonInWatchlist = watchlistRepository.checkIfPersonIsInWatchlist(personId).bind(),
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
            detailUIState = movieRepository.getMovieDetails(
                movieId = movieId,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    detailUIState.modifyLoadedState {
                        copy(selectedMovieDetails = it)
                    }
                },
            )
        }
    }

    fun addActorToWatchlist(
        personDetail: PersonDetail?,
    ) {
        viewModelScope.launch {
            if (personDetail == null) {
                Timber.e("Person was null while adding to watchlist operation.")
                return@launch
            }

            showLoading()
            watchlistRepository.addPersonToWatchlist(
                personDetail = personDetail,
            ).fold(
                ifLeft = { detailUIState = UiState.Error(it) },
                ifRight = { sendUiEvent(UiEvent.ShowMessage("Added ${personDetail.personName} to watchlist")) },
            )
            hideLoading()
        }
    }

    fun removeActorFromWatchlist(
        personDetail: PersonDetail?,
    ) {
        viewModelScope.launch {
            if (personDetail == null) {
                Timber.e("Failed to remove person while adding to watchlist operation.")
                return@launch
            }

            showLoading()
            watchlistRepository.removePersonFromWatchlist(
                personId = personDetail.personId,
            ).fold(
                ifLeft = { detailUIState = UiState.Error(it) },
                ifRight = { sendUiEvent(UiEvent.ShowMessage("Removed ${personDetail.personName} to watchlist")) },
            )
            hideLoading()
        }
    }
}