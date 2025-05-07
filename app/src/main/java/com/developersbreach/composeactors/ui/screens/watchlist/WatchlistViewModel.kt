package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.person.model.WatchlistPerson
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
) : ViewModel() {

    val watchlistMovies: LiveData<List<Movie>> = movieRepository.getAllMoviesFromWatchlist()
    val watchlistPersons: LiveData<List<WatchlistPerson>> = personRepository.getAllPersonsFromWatchlist()

    fun removeMovieFromWatchlist(movie: Movie) {
        viewModelScope.launch {
            movieRepository.deleteSelectedMovieFromWatchlist(movie)
        }
    }

    fun removePersonFromWatchlist(watchlistPerson: WatchlistPerson) {
        viewModelScope.launch {
            personRepository.deleteSelectedPersonFromWatchlist(watchlistPerson)
        }
    }
}