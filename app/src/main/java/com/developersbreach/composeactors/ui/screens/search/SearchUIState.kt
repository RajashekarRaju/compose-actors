package com.developersbreach.composeactors.ui.screens.search

import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie

sealed interface SearchDataType

data class MovieSearch(
    val movieList: List<Movie> = listOf(),
    val isSearchingResults: Boolean = false,
) : SearchDataType

data class ActorSearch(
    val personList: List<Person> = listOf(),
    val isSearchingResults: Boolean = false,
) : SearchDataType