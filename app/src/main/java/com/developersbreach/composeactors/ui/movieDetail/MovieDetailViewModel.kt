package com.developersbreach.composeactors.ui.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.model.Cast
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.model.MovieDetail
import com.developersbreach.composeactors.repository.database.DatabaseRepository
import com.developersbreach.composeactors.repository.network.NetworkRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class MovieDetailViewModel(
    private val movieId: Int,
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    // Holds the state for values in DetailsViewState
    var uiState by mutableStateOf(MovieDetailUiState(movieData = null))
        private set

    val isFavoriteMovie: LiveData<Int>
        get() = databaseRepository.checkIfMovieIsFavorite(movieId)

    init {
        viewModelScope.launch {
            try {
                startFetchingDetails()
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    // Update the values in uiState from all data sources.
    private suspend fun startFetchingDetails() {
        uiState = MovieDetailUiState(isFetchingDetails = true, movieData = null)
        uiState = MovieDetailUiState(
            movieData = networkRepository.getSelectedMovieData(movieId),
            similarMovies = networkRepository.getSimilarMoviesByIdData(movieId),
            recommendedMovies = networkRepository.getRecommendedMoviesByIdData(movieId),
            movieCast = networkRepository.getMovieCastByIdData(movieId),
            isFetchingDetails = false
        )
    }

    fun addMovieToFavorites() {
        viewModelScope.launch {
            val movie: MovieDetail? = uiState.movieData
            if (movie != null) {
                databaseRepository.addMovieToFavorites(
                    Movie(
                        movieId = movie.movieId,
                        posterPathUrl = movie.poster
                    )
                )
            }
        }
    }

    fun removeMovieFromFavorites() {
        viewModelScope.launch {
            val movie: MovieDetail? = uiState.movieData
            if (movie != null) {
                databaseRepository.deleteSelectedFavoriteMovie(
                    Movie(
                        movieId = movie.movieId,
                        posterPathUrl = movie.poster
                    )
                )
            }
        }
    }
}

/**
 * Models the UI state for the [MovieDetailScreen] screen.
 */
data class MovieDetailUiState(
    val movieData: MovieDetail?,
    val similarMovies: List<Movie> = emptyList(),
    val recommendedMovies: List<Movie> = emptyList(),
    val movieCast: List<Cast> = emptyList(),
    val isFetchingDetails: Boolean = false,
)