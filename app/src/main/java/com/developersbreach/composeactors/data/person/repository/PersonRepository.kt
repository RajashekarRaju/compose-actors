package com.developersbreach.composeactors.data.person.repository

import arrow.core.Either
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail

interface PersonRepository {
    suspend fun getPopularPersons(): Either<Throwable, List<Person>>

    suspend fun getTrendingPersons(): Either<Throwable, List<Person>>

    suspend fun getPersonDetails(personId: Int): Either<Throwable, PersonDetail>

    suspend fun getCastDetails(personId: Int): Either<Throwable, List<Movie>>
}