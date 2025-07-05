package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

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
            showLoading()
            watchlistRepository.removeMovieFromWatchlist(
                movie = movie,
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = { showMessage("Removed ${movie.movieTitle} from watchlist") },
            )
            hideLoading()
        }
    }

    fun removePersonFromWatchlist(
        person: WatchlistPerson,
    ) {
        viewModelScope.launch {
            showLoading()
            watchlistRepository.removePersonFromWatchlist(
                personId = person.personId,
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = { showMessage("Removed “${person.personName}” from watchlist") },
            )
            hideLoading()
        }
    }
}