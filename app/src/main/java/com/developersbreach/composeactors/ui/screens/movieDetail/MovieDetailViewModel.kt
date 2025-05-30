package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.raise.either
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
) : ViewModel() {

    private val movieId: Int = savedStateHandle.toRoute<AppDestinations.MovieDetail>().movieId

    var uiState: UiState<MovieDetailsData> by mutableStateOf(UiState.Loading)
        private set

    var sheetUiState by mutableStateOf(ActorsSheetUIState(selectedPersonDetails = null))
        private set

    var movieSheetUiState by mutableStateOf(MovieSheetUIState(selectedMovieDetails = null))

    val isMovieInWatchlist: LiveData<Int> = movieRepository.isMovieInWatchlist(movieId)

    init {
        viewModelScope.launch {
            startFetchingDetails()
        }
    }

    private suspend fun startFetchingDetails() {
        uiState = UiState.Success(MovieDetailsData(isFetchingDetails = true))
        either {
            MovieDetailsData(
                movieData = movieRepository.getMovieDetails(movieId).bind(),
                similarMovies = movieRepository.getSimilarMovies(movieId).bind(),
                recommendedMovies = movieRepository.getRecommendedMovies(movieId).bind(),
                movieCast = movieRepository.getMovieCast(movieId).bind(),
                movieProviders = movieRepository.getMovieProviders(movieId).bind(),
                isFetchingDetails = false,
            )
        }.fold(
            ifLeft = { uiState = UiState.Error(it) },
            ifRight = { uiState = UiState.Success(it) },
        )
    }

    fun addMovieToWatchlist(
        movie: MovieDetail?,
    ) {
        if (movie == null) {
            Timber.e("Failed to addMovieToWatchlist, since movie was null")
            return
        }
        viewModelScope.launch {
            movieRepository.addMovieToWatchlist(
                Movie(
                    movieId = movie.movieId,
                    movieName = movie.movieTitle,
                    posterPath = movie.poster,
                    backdropPath = movie.banner,
                ),
            )
        }
    }

    fun removeMovieFromWatchlist(
        movie: MovieDetail?,
    ) {
        if (movie == null) {
            Timber.e("Failed to removeMovieFromWatchlist, since movie was null")
            return
        }
        viewModelScope.launch {
            movieRepository.deleteSelectedMovieFromWatchlist(
                Movie(
                    movieId = movie.movieId,
                    movieName = movie.movieTitle,
                    posterPath = movie.poster,
                    backdropPath = movie.banner,
                ),
            )
        }
    }

    fun getSelectedPersonDetails(
        personId: Int?,
    ) {
        if (personId == null) {
            Timber.e("Failed to getSelectedPersonDetails, since id was null")
            return
        }
        viewModelScope.launch {
            personRepository.getPersonDetails(
                personId = personId,
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = { sheetUiState = ActorsSheetUIState(it) },
            )
        }
    }

    fun getSelectedMovieDetails(
        movieId: Int?,
    ) {
        if (movieId == null) {
            Timber.e("Failed to getSelectedMovieDetails, since id was null")
            return
        }
        viewModelScope.launch {
            movieRepository.getMovieDetails(
                movieId = movieId,
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = { movieSheetUiState = MovieSheetUIState(selectedMovieDetails = it) },
            )
        }
    }
}