package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.model.WatchlistPerson
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.ui.components.UiEvent
import com.developersbreach.composeactors.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val watchlistRepository: WatchlistRepository,
) : ViewModel() {

    val watchlistMovies: Flow<PagingData<Movie>> = flow {
        emitAll(watchlistRepository.getAllMovies())
    }.cachedIn(viewModelScope)

    val watchlistPersons: LiveData<List<WatchlistPerson>> = personRepository.getAllPersonsFromWatchlist()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun removeMovieFromWatchlist(
        movie: Movie,
    ) {
        viewModelScope.launch {
            watchlistRepository.removeMovieFromWatchlist(
                movie = movie,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    _uiEvent.emit(UiEvent.ShowMessage("Removed ${movie.movieName} from watchlist"))
                },
            )
        }
    }

    fun removePersonFromWatchlist(watchlistPerson: WatchlistPerson) {
        viewModelScope.launch {
            personRepository.deleteSelectedPersonFromWatchlist(watchlistPerson)
        }
    }
}