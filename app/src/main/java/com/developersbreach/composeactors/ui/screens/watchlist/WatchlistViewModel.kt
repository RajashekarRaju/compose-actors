package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiEvent
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) : BaseViewModel() {

    var uiState: UiState<WatchlistUiState> by mutableStateOf(
        UiState.Success(
            WatchlistUiState(
                watchlistMovies = flow { emitAll(watchlistRepository.getAllMovies()) }.cachedIn(viewModelScope),
                watchlistPersons = flow { emitAll(watchlistRepository.getPeople()) }.cachedIn(viewModelScope),
            ),
        ),
    )
        private set

    fun removeMovieFromWatchlist(
        movie: Movie,
    ) {
        viewModelScope.launch {
            watchlistRepository.removeMovieFromWatchlist(
                movie = movie,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { sendUiEvent(UiEvent.ShowMessage("Removed ${movie.movieTitle} from watchlist")) },
            )
        }
    }

    fun removePersonFromWatchlist(
        person: WatchlistPerson,
    ) {
        viewModelScope.launch {
            watchlistRepository.removePersonFromWatchlist(
                personId = person.personId,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = { sendUiEvent(UiEvent.ShowMessage("Removed “${person.personName}” from watchlist")) },
            )
        }
    }
}