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
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val actorRepository: ActorRepository,
    private val removeMovieFromFavoritesUseCase: RemoveMovieFromFavoritesUseCase,
) : ViewModel() {
    val favoriteMovies: LiveData<List<Movie>> = movieRepository.getAllFavoriteMovies()
    val favoriteActors: LiveData<List<FavoriteActor>> = actorRepository.getAllFavoriteActors()

    fun getSelectedMovieDetails(
        movieId: Int?
    ) {
        viewModelScope.launch {
            try {
                if (movieId != null) {
                    movieRepository.getSelectedMovieData(movieId)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

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