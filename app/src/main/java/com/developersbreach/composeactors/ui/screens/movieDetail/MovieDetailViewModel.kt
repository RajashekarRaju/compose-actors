package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.ui.navigation.AppDestinations.MOVIE_DETAILS_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle[MOVIE_DETAILS_ID_KEY])

    var uiState by mutableStateOf(MovieDetailsUIState(movieData = null))
        private set

    var sheetUiState by mutableStateOf(ActorsSheetUIState(selectedPersonDetails = null))
        private set

    var movieSheetUiState by mutableStateOf(MovieSheetUIState(selectedMovieDetails = null))

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
            movieData = movieRepository.getMovieDetails(movieId),
            similarMovies = movieRepository.getSimilarMovies(movieId),
            recommendedMovies = movieRepository.getRecommendedMovies(movieId),
            movieCast = movieRepository.getMovieCast(movieId),
            movieProviders = movieRepository.getMovieProviders(movieId),
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
                        posterPath = movie.poster,
                        backdropPath = movie.banner,
                    )
                )
            }
        }
    }

    fun removeMovieFromFavorites() {
        viewModelScope.launch {
            val movie: MovieDetail? = uiState.movieData
            if (movie != null) {
                movieRepository.deleteSelectedFavoriteMovie(
                    Movie(
                        movieId = movie.movieId,
                        movieName = movie.movieTitle,
                        posterPath = movie.poster,
                        backdropPath = movie.banner,
                    )
                )
            } else {
                Timber.e("Id of ${uiState.movieData?.movieId} was null while delete operation.")
            }
        }
    }

    /**
     * @param personId for querying selected actor details.
     * This function will be triggered only when user clicks any actor items.
     * Updates the data values to show in modal sheet.
     */
    fun getSelectedPersonDetails(
        personId: Int?
    ) {
        viewModelScope.launch {
            try {
                if (personId != null) {
                    val actorsData = personRepository.getPersonDetails(personId)
                    sheetUiState = ActorsSheetUIState(selectedPersonDetails = actorsData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    fun getSelectedMovieDetails(
        movieId: Int?
    ) {
        viewModelScope.launch {
            try {
                movieId?.let { id ->
                    val movieData = movieRepository.getMovieDetails(id)
                    movieSheetUiState = MovieSheetUIState(selectedMovieDetails = movieData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }
}