package com.developersbreach.composeactors.data.person.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.MoviesResponse

interface PersonApi {
    suspend fun getPopularPersons(): Either<Throwable, PagedResponse<Person>>
    suspend fun getTrendingPersons(): Either<Throwable, PagedResponse<Person>>
    suspend fun getPersonDetails(personId: Int): Either<Throwable, PersonDetail>
    suspend fun getCastDetails(personId: Int): Either<Throwable, MoviesResponse>
}