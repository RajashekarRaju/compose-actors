package com.developersbreach.composeactors.ui.screens.search

import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.R

data class SearchUiState(
    val searchType: SearchType,
    val isSearchingResults: Boolean = false,
    val movies: List<Movie> = listOf(),
    val people: List<Person> = listOf(),
)

sealed class SearchUiMessage {
    data object NoActorsFound : SearchUiMessage()

    data object NoMoviesFound : SearchUiMessage()
}

fun SearchUiMessage.toResId(): Int = when (this) {
    SearchUiMessage.NoActorsFound -> R.string.no_actors_found_message
    SearchUiMessage.NoMoviesFound -> R.string.no_movies_found_message
}