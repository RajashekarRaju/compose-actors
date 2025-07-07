package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.raise.either
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.toMovie
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
import com.developersbreach.composeactors.ui.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
    private val watchlistRepository: WatchlistRepository,
    errorReporter: ErrorReporter,
) : BaseViewModel(errorReporter) {

    private val movieId: Int = savedStateHandle.toRoute<AppDestinations.MovieDetail>().movieId

    var uiState: UiState<MovieDetailsUiState> by mutableStateOf(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            startFetchingDetails()
        }
    }

    private suspend fun startFetchingDetails() {
        showLoading()
        either {
            MovieDetailsUiState(
                movieData = movieRepository.getMovieDetails(movieId).bind(),
                similarMovies = movieRepository.getSimilarMovies(movieId).bind(),
                recommendedMovies = movieRepository.getRecommendedMovies(movieId).bind(),
                movieCast = movieRepository.getMovieCast(movieId).bind(),
                movieProviders = movieRepository.getMovieProviders(movieId).bind(),
                isMovieInWatchlist = watchlistRepository.checkIfMovieIsInWatchlist(movieId).bind(),
            )
        }.fold(
            ifLeft = { uiState = UiState.Error(it) },
            ifRight = { uiState = UiState.Success(it) },
        )
        hideLoading()
    }

    fun addMovieToWatchlist(
        movieDetail: MovieDetail?,
    ) {
        viewModelScope.launch {
            if (movieDetail == null) {
                Timber.e("Failed to addMovieToWatchlist, since movie was null")
                showMessage("Failed to load movie details.")
                return@launch
            }

            showLoading()
            watchlistRepository.addMovieToWatchlist(
                movieDetail = movieDetail,
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = { showMessage("Added “${movieDetail.movieTitle}” to watchlist") },
            )
            hideLoading()
        }
    }

    fun removeMovieFromWatchlist(
        movieDetail: MovieDetail?,
    ) {
        viewModelScope.launch {
            if (movieDetail == null) {
                Timber.e("Failed to removeMovieFromWatchlist, since movie was null")
                showMessage("Failed to load movie details.")
                return@launch
            }

            showLoading()
            watchlistRepository.removeMovieFromWatchlist(
                movie = movieDetail.toMovie(),
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = { showMessage("Removed ${movieDetail.movieTitle} from watchlist") },
            )
            hideLoading()
        }
    }

    fun getSelectedPersonDetails(
        personId: Int?,
    ) {
        viewModelScope.launch {
            if (personId == null) {
                Timber.e("Failed to getSelectedPersonDetails, since id was null")
                showMessage("Failed to load person details.")
                return@launch
            }
            uiState = personRepository.getPersonDetails(
                personId = personId,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    uiState.modifyLoadedState {
                        copy(selectedPersonDetails = it)
                    }
                },
            )
        }
    }

    fun getSelectedMovieDetails(
        movieId: Int?,
    ) {
        viewModelScope.launch {
            if (movieId == null) {
                Timber.e("Failed to getSelectedMovieDetails, since id was null")
                showMessage("Failed to load movie details.")
                return@launch
            }
            uiState = movieRepository.getMovieDetails(
                movieId = movieId,
            ).fold(
                ifLeft = { UiState.Error(it) },
                ifRight = {
                    uiState.modifyLoadedState {
                        copy(selectedMovieDetails = it)
                    }
                },
            )
        }
    }
}