package com.developersbreach.composeactors.ui.screens.home

import androidx.paging.PagingData
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

// TODO - create a sealed class to contains the different states

/**
 * Models the UI state for the [HomeScreen] screen.
 */
data class HomeUIState(
    var popularPersonList: List<Person> = emptyList(),
    var trendingPersonList: List<Person> = emptyList(),
    val isFetchingPersons: Boolean = false,
    var upcomingMoviesList: List<Movie> = emptyList(),
    var nowPlayingMoviesList: Flow<PagingData<Movie>> = emptyFlow(),
)

/**
 * Models the UI state for the [SheetContentMovieDetails] modal sheet.
 */
data class HomeSheetUIState(
    var selectedMovieDetails: MovieDetail? = null
)