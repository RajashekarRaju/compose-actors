package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.model.MenuItem
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.actor.ActorRepository
import com.developersbreach.composeactors.data.repository.movie.MovieRepository
import com.developersbreach.composeactors.ui.screens.search.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * To manage ui state and data for screen HomeScreen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val actorRepository: ActorRepository
) : ViewModel() {

    var uiState by mutableStateOf(HomeUIState())
        private set

    var sheetUiState by mutableStateOf(HomeSheetUIState())
        private set

    val favoriteMovies: LiveData<List<Movie>> = movieRepository.getAllFavoriteMovies()

    private val _updateHomeSearchType = MutableLiveData<SearchType>()
    val updateHomeSearchType: LiveData<SearchType> = _updateHomeSearchType

    init {
        viewModelScope.launch {
            try {
                startFetchingActors()
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    fun getMenuItems(): List<MenuItem> {
        val menuItemList = ArrayList<MenuItem>()
        val menuItem = MenuItem("Favorites", R.drawable.baseline_looks_one_24)
        menuItemList.add(menuItem)
        return menuItemList
    }

    private suspend fun startFetchingActors() {
        uiState = HomeUIState(isFetchingActors = true)
        uiState = HomeUIState(
            popularActorList = actorRepository.getPopularActorsData(),
            trendingActorList = actorRepository.getTrendingActorsData(),
            isFetchingActors = false,
            upcomingMoviesList = actorRepository.getUpcomingMoviesData(),
            nowPlayingMoviesList = actorRepository.getNowPlayingMoviesData()
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
                    val movieData = movieRepository.getSelectedMovieData(movieId)
                    sheetUiState = HomeSheetUIState(selectedMovieDetails = movieData)
                }
            } catch (e: IOException) {
                Timber.e("$e")
            }
        }
    }

    fun updateHomeSearchType(searchType: SearchType) {
        when (searchType) {
            SearchType.Actors -> _updateHomeSearchType.value = SearchType.Actors
            SearchType.Movies -> _updateHomeSearchType.value = SearchType.Movies
        }
    }
}