package com.developersbreach.composeactors.data.person.remote

import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.MoviesResponse

interface PersonApi {
    suspend fun getPopularPersons(): PagedResponse<Person>
    suspend fun getTrendingPersons(): PagedResponse<Person>
    suspend fun getPersonDetails(personId: Int): PersonDetail
    suspend fun getCastDetails(personId: Int): MoviesResponse
}