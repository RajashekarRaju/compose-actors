package com.developersbreach.composeactors.ui.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
) : ViewModel() {

    val favoriteMovies: LiveData<List<Movie>> = movieRepository.getAllFavoriteMovies()
    val favoriteActors: LiveData<List<FavoriteActor>> = personRepository.getAllFavoritePersons()

    fun removeMovieFromFavorites(movie: Movie) {
        viewModelScope.launch {
            movieRepository.deleteSelectedFavoriteMovie(movie)
        }
    }

    fun removeActorFromFavorites(favoriteActor: FavoriteActor) {
        viewModelScope.launch {
            personRepository.deleteSelectedFavoritePerson(favoriteActor)
        }
    }
}