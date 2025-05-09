package com.developersbreach.composeactors.data.search.repository

import arrow.core.Either
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie

interface SearchRepository {
    suspend fun getSearchableActorsData(query: String): Either<Throwable, List<Person>>

    suspend fun getSearchableMoviesData(query: String): Either<Throwable, List<Movie>>
}