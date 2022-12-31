package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.DatabaseRepository
import com.developersbreach.composeactors.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * To manage ui state and data for screen [HomeScreen].
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    // Holds the state for values in HomeViewState
    var uiState by mutableStateOf(HomeUIState())
        private set

    // Holds the state for values in HomeViewState
    var sheetUiState by mutableStateOf(HomeSheetUIState())
        private set

    val favoriteMovies: LiveData<List<Movie>>
        get() = databaseRepository.getAllFavoriteMovies()

    init {
        viewModelScope.launch {
            try {
                startFetchingActors()
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    // Update the values in uiState from all data sources.
    private suspend fun startFetchingActors() {
        uiState = HomeUIState(isFetchingActors = true)
        uiState = HomeUIState(
            popularActorList = networkRepository.getPopularActorsData(),
            trendingActorList = networkRepository.getTrendingActorsData(),
            isFetchingActors = false,
            upcomingMoviesList = networkRepository.getUpcomingMoviesData(),
            nowPlayingMoviesList = networkRepository.getNowPlayingMoviesData()
        )
    }

    /**
     * @param movieId for querying selected movie details.
     * This function will be triggered only when user clicks any movie items.
     * Updates the data values to show in modal sheet.
     */
    fun getSelectedMovieDetails(
        movieId: Int?
    ) {
        viewModelScope.launch {
            try {
                if (movieId != null) {
                    val movieData = networkRepository.getSelectedMovieData(movieId)
                    sheetUiState = HomeSheetUIState(selectedMovieDetails = movieData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }
}