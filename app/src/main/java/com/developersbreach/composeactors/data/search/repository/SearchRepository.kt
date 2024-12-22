package com.developersbreach.composeactors.data.search.repository

import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie

interface SearchRepository {
    suspend fun getSearchableActorsData(query: String): List<Person>
    suspend fun getSearchableMoviesData(query: String): List<Movie>
}