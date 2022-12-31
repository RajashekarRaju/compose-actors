package com.developersbreach.composeactors.ui.screens.movieDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.MovieDetail
import com.developersbreach.composeactors.data.repository.DatabaseRepository
import com.developersbreach.composeactors.data.repository.NetworkRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class MovieDetailViewModel(
    private val movieId: Int,
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    // Holds the state for values in DetailsViewState
    var uiState by mutableStateOf(MovieDetailsUIState(movieData = null))
        private set

    // Holds the state for values in ActorDetailsViewState
    var sheetUiState by mutableStateOf(ActorsSheetUIState(selectedActorDetails = null))
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
        uiState = MovieDetailsUIState(isFetchingDetails = true, movieData = null)
        uiState = MovieDetailsUIState(
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
                    val actorsData = networkRepository.getSelectedActorData(actorId)
                    sheetUiState = ActorsSheetUIState(selectedActorDetails = actorsData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }
}