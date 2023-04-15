package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.MovieDetail
import com.developersbreach.composeactors.data.repository.actor.ActorRepository
import com.developersbreach.composeactors.data.repository.movie.MovieRepository
import com.developersbreach.composeactors.ui.navigation.AppDestinations.MOVIE_DETAILS_ID_KEY
import com.developersbreach.composeactors.domain.useCase.RemoveMovieFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val actorRepository: ActorRepository,
    private val removeMovieFromFavoritesUseCase: RemoveMovieFromFavoritesUseCase,
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle[MOVIE_DETAILS_ID_KEY])

    var uiState by mutableStateOf(MovieDetailsUIState(movieData = null))
        private set

    var sheetUiState by mutableStateOf(ActorsSheetUIState(selectedActorDetails = null))
        private set

    val isFavoriteMovie: LiveData<Int> = movieRepository.isFavoriteMovie(movieId)

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
        uiState = MovieDetailsUIState(isFetchingDetails = true, movieData = null)
        uiState = MovieDetailsUIState(
            movieData = movieRepository.getSelectedMovieData(movieId),
            similarMovies = movieRepository.getSimilarMoviesByIdData(movieId),
            recommendedMovies = movieRepository.getRecommendedMoviesByIdData(movieId),
            movieCast = movieRepository.getMovieCastByIdData(movieId),
            isFetchingDetails = false
        )
    }

    fun addMovieToFavorites() {
        viewModelScope.launch {
            val movie: MovieDetail? = uiState.movieData
            if (movie != null) {
                movieRepository.addMovieToFavorites(
                    Movie(
                        movieId = movie.movieId,
                        movieName = movie.movieTitle,
                        posterPathUrl = movie.poster,
                        bannerUrl = movie.banner,
                    )
                )
            }
        }
    }

    fun removeMovieFromFavorites() {
        viewModelScope.launch {
            val movie: MovieDetail? = uiState.movieData
            if (movie != null) {
                removeMovieFromFavoritesUseCase(
                    Movie(
                        movieId = movie.movieId,
                        movieName = movie.movieTitle,
                        posterPathUrl = movie.poster,
                        bannerUrl = movie.banner,
                    )
                )
            } else {
                Timber.e("Id of $movie was null while delete operation.")
            }
        }
    }

    /**
     * @param actorId for querying selected actor details.
     * This function will be triggered only when user clicks any actor items.
     * Updates the data values to show in modal sheet.
     */
    fun getSelectedActorDetails(
        actorId: Int?
    ) {
        viewModelScope.launch {
            try {
                if (actorId != null) {
                    val actorsData = actorRepository.getSelectedActorData(actorId)
                    sheetUiState = ActorsSheetUIState(selectedActorDetails = actorsData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }
}