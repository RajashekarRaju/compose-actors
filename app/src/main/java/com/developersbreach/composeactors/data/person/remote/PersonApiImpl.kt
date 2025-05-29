package com.developersbreach.composeactors.data.person.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.BaseUrlProvider.TmdbConfig.TMDB_API_KEY
import com.developersbreach.composeactors.core.network.BaseUrlProvider.TmdbConfig.TMDB_BASE_URL
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

    override suspend fun getPopularPersons(): Either<Throwable, PagedResponse<Person>> {
        return requestHandler.getPagedResponse(
            URL("${TMDB_BASE_URL}person/popular?$TMDB_API_KEY"),
        )
    }

    // person/3233?api_key=TMDB_API_KEY
    override suspend fun getTrendingPersons(): Either<Throwable, PagedResponse<Person>> {
        return requestHandler.getPagedResponse(
            URL("${TMDB_BASE_URL}trending/person/week?$TMDB_API_KEY"),
        )
    }

    // person/3233?api_key=TMDB_API_KEY
    override suspend fun getPersonDetails(
        personId: Int,
    ): Either<Throwable, PersonDetail> {
        return requestHandler.getResponse(
            URL("${TMDB_BASE_URL}person/$personId?$TMDB_API_KEY"),
        )
    }

    // person/3233/movie_credits?api_key=TMDB_API_KEY
    override suspend fun getCastDetails(
        personId: Int,
    ): Either<Throwable, MoviesResponse> {
        return requestHandler.getResponse<MoviesResponse>(
            URL("${TMDB_BASE_URL}person/$personId/movie_credits?$TMDB_API_KEY"),
        )
    }
}