package com.developersbreach.composeactors.ui.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.actor.ActorRepository
import com.developersbreach.composeactors.data.repository.movie.MovieRepository
import com.developersbreach.composeactors.domain.useCase.RemoveMovieFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    movieRepository: MovieRepository,
    private val actorRepository: ActorRepository,
    private val removeMovieFromFavoritesUseCase: RemoveMovieFromFavoritesUseCase,
) : ViewModel() {

    val favoriteMovies: LiveData<List<Movie>> = movieRepository.getAllFavoriteMovies()
    val favoriteActors: LiveData<List<FavoriteActor>> = actorRepository.getAllFavoriteActors()

    fun removeMovieFromFavorites(movie: Movie) {
        viewModelScope.launch {
            removeMovieFromFavoritesUseCase.invoke(movie)
        }
    }

    fun removeActorFromFavorites(favoriteActor: FavoriteActor) {
        viewModelScope.launch {
            actorRepository.deleteSelectedFavoriteActor(favoriteActor)
        }
    }
}