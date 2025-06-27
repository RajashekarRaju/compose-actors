package com.developersbreach.composeactors.ui.screens.home

import androidx.paging.PagingData
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.ui.screens.search.SearchType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId

data class HomeUiState(
    var popularPersonList: List<Person> = emptyList(),
    var trendingPersonList: List<Person> = emptyList(),
    val isFetchingPersons: Boolean = false,
    var upcomingMoviesList: List<Movie> = emptyList(),
    var nowPlayingMoviesList: Flow<PagingData<Movie>> = emptyFlow(),
    var selectedMovieDetails: MovieDetail? = null,
    var searchType: SearchType = SearchType.People,
)

sealed class HomeUiMessage : ErrorMessage {
    data object HomeError : HomeUiMessage()

    data class ValidationError(val message: String) : HomeUiMessage()

    data class Common(val error: CommonUiMessage) : HomeUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is HomeError -> UiMessageWithResId(R.string.home_error)
        is ValidationError -> UiMessageWithResId(R.string.home_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}