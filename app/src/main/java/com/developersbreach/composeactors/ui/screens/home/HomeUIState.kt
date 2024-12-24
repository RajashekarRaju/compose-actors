package com.developersbreach.composeactors.ui.screens.home

import androidx.paging.PagingData
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeData(
    var popularPersonList: List<Person> = emptyList(),
    var trendingPersonList: List<Person> = emptyList(),
    val isFetchingPersons: Boolean = false,
    var upcomingMoviesList: List<Movie> = emptyList(),
    var nowPlayingMoviesList: Flow<PagingData<Movie>> = emptyFlow(),
)

data class HomeSheetUIState(
    var selectedMovieDetails: MovieDetail? = null
)