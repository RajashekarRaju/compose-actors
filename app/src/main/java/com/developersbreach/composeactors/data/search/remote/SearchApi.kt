package com.developersbreach.composeactors.data.search.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie

interface SearchApi {
    suspend fun getSearchableActorsData(query: String): Either<Throwable, PagedResponse<Person>>
    suspend fun getSearchableMoviesData(query: String): Either<Throwable, PagedResponse<Movie>>
}