package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.repository.actor.ActorRepository
import com.developersbreach.composeactors.domain.GetPagedMovies
import com.developersbreach.composeactors.ui.screens.search.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * To manage ui state and data for screen HomeScreen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val actorRepository: ActorRepository,
    private val getPagedMovies: GetPagedMovies
) : ViewModel() {

    var uiState by mutableStateOf(HomeUIState())
        private set

    var sheetUiState by mutableStateOf(HomeSheetUIState())
        private set

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

    private suspend fun startFetchingActors() {
        uiState = HomeUIState(isFetchingActors = true)
        uiState = HomeUIState(
            popularActorList = actorRepository.getPopularActorsData(),
            trendingActorList = actorRepository.getTrendingActorsData(),
            isFetchingActors = false,
            upcomingMoviesList = actorRepository.getUpcomingMoviesData(),
            nowPlayingMoviesList = getPagedMovies(viewModelScope)
        )
    }

    fun updateHomeSearchType(searchType: SearchType) {
        when (searchType) {
            SearchType.Actors -> _updateHomeSearchType.value = SearchType.Actors
            SearchType.Movies -> _updateHomeSearchType.value = SearchType.Movies
        }
    }
}