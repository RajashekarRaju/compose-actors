package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.movie.MovieRepository
import com.developersbreach.composeactors.ui.screens.home.HomeSheetUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {
    val favoriteMovies: LiveData<List<Movie>> = movieRepository.getAllFavoriteMovies()

    var sheetUiState by mutableStateOf(HomeSheetUIState())
        private set

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
}