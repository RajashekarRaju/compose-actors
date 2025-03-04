package com.developersbreach.composeactors.data.person.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.MoviesResponse
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler,
) : PersonApi, BaseUrlProvider() {

    // person/popular?api_key=API_KEY
    override suspend fun getPopularPersons(): Either<Throwable, PagedResponse<Person>> {
        return requestHandler.getPagedResponse(
            URL("${BASE_URL}person/popular?$API_KEY"),
        )
    }

    // person/3233?api_key=API_KEY
    override suspend fun getTrendingPersons(): Either<Throwable, PagedResponse<Person>> {
        return requestHandler.getPagedResponse(
            URL("${BASE_URL}trending/person/week?$API_KEY"),
        )
    }

    // person/3233?api_key=API_KEY
    override suspend fun getPersonDetails(
        personId: Int,
    ): Either<Throwable, PersonDetail> {
        return requestHandler.getResponse(
            URL("${BASE_URL}person/$personId?$API_KEY"),
        )
    }

    // person/3233/movie_credits?api_key=API_KEY
    override suspend fun getCastDetails(
        personId: Int,
    ): Either<Throwable, MoviesResponse> {
        return requestHandler.getResponse<MoviesResponse>(
            URL("${BASE_URL}person/$personId/movie_credits?$API_KEY"),
        )
    }
}