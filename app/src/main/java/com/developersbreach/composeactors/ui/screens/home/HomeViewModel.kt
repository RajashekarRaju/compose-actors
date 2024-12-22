package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.domain.movie.GetPagedMovies
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
    private val personRepository: PersonRepository,
    private val movieRepository: MovieRepository,
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
        uiState = HomeUIState(isFetchingPersons = true)
        uiState = HomeUIState(
            popularPersonList = personRepository.getPopularPersons(),
            trendingPersonList = personRepository.getTrendingPersons(),
            isFetchingPersons = false,
            upcomingMoviesList = movieRepository.getUpcomingMovies(),
            nowPlayingMoviesList = getPagedMovies().cachedIn(viewModelScope)
        )
    }

    fun updateHomeSearchType(searchType: SearchType) {
        when (searchType) {
            SearchType.Persons -> _updateHomeSearchType.value = SearchType.Persons
            SearchType.Movies -> _updateHomeSearchType.value = SearchType.Movies
        }
    }
}