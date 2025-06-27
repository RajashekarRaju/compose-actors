package com.developersbreach.composeactors.ui.screens.search

import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie

data class SearchUiState(
    val searchType: SearchType,
    val isSearchingResults: Boolean = false,
    val movies: List<Movie> = listOf(),
    val people: List<Person> = listOf(),
)