package com.developersbreach.composeactors.ui.screens.search

import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId

data class SearchUiState(
    val searchType: SearchType,
    val isSearchingResults: Boolean = false,
    val movies: List<Movie> = listOf(),
    val people: List<Person> = listOf(),
)

sealed class SearchUiMessage : ErrorMessage {
    data object NoActorsFound : SearchUiMessage()

    data object NoMoviesFound : SearchUiMessage()

    data object SearchError : SearchUiMessage()

    data class ValidationError(val message: String) : SearchUiMessage()

    data class Common(val error: CommonUiMessage) : SearchUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is NoActorsFound -> UiMessageWithResId(R.string.no_actors_found_message)
        is NoMoviesFound -> UiMessageWithResId(R.string.no_movies_found_message)
        is SearchError -> UiMessageWithResId(R.string.search_error)
        is ValidationError -> UiMessageWithResId(R.string.search_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}