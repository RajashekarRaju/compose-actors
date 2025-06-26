package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.raise.either
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.toMovie
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.ui.components.UiEvent
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
import com.developersbreach.composeactors.ui.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
    private val watchlistRepository: WatchlistRepository,
) : ViewModel() {

    private val movieId: Int = savedStateHandle.toRoute<AppDestinations.MovieDetail>().movieId

    var uiState: UiState<MovieDetailsData> by mutableStateOf(UiState.Loading)
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            startFetchingDetails()
        }
    }

    private suspend fun startFetchingDetails() {
        isLoading = true
        either {
            MovieDetailsData(
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
        isLoading = false
    }

    fun addMovieToWatchlist(
        movieDetail: MovieDetail?,
    ) {
        viewModelScope.launch {
            if (movieDetail == null) {
                Timber.e("Failed to addMovieToWatchlist, since movie was null")
                return@launch
            }

            isLoading = true
            watchlistRepository.addMovieToWatchlist(
                movieDetail = movieDetail,
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = {
                    _uiEvent.emit(UiEvent.ShowMessage("Added “${movieDetail.movieTitle}” to watchlist"))
                },
            )
            isLoading = false
        }
    }

    fun removeMovieFromWatchlist(
        movieDetail: MovieDetail?,
    ) {
        viewModelScope.launch {
            if (movieDetail == null) {
                Timber.e("Failed to removeMovieFromWatchlist, since movie was null")
                return@launch
            }

            isLoading = true
            watchlistRepository.removeMovieFromWatchlist(
                movie = movieDetail.toMovie(),
            ).fold(
                ifLeft = { uiState = UiState.Error(it) },
                ifRight = {
                    _uiEvent.emit(UiEvent.ShowMessage("Removed ${movieDetail.movieTitle} from watchlist"))
                },
            )
            isLoading = false
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
        if (movieId == null) {
            Timber.e("Failed to getSelectedMovieDetails, since id was null")
            return
        }
        viewModelScope.launch {
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