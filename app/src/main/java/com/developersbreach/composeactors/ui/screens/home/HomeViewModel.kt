package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import arrow.core.raise.either
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.domain.movie.GetPagedMovies
import com.developersbreach.composeactors.ui.components.BaseViewModel
import com.developersbreach.composeactors.ui.components.UiState
import com.developersbreach.composeactors.ui.components.modifyLoadedState
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
    private val getPagedMovies: GetPagedMovies,
) : BaseViewModel() {

    var uiState: UiState<HomeUiState> by mutableStateOf(UiState.Loading)
        private set

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
        uiState = UiState.Success(HomeUiState(isFetchingPersons = true))
        uiState = either {
            HomeUiState(
                popularPersonList = personRepository.getPopularPersons().bind(),
                trendingPersonList = personRepository.getTrendingPersons().bind(),
                isFetchingPersons = false,
                upcomingMoviesList = movieRepository.getUpcomingMovies().bind(),
                nowPlayingMoviesList = getPagedMovies().cachedIn(viewModelScope),
            )
        }.fold(
            ifLeft = { UiState.Error(it) },
            ifRight = { UiState.Success(it) },
        )
    }

    fun updateHomeSearchType(searchType: SearchType) {
        uiState = uiState.modifyLoadedState {
            copy(searchType = searchType)
        }
    }
}