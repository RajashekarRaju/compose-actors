package com.developersbreach.composeactors.data.search.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.model.Person
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler
) : SearchApi, BaseUrlProvider() {

    // search/person?api_key=API_KEY&query=$pacino
    override suspend fun getSearchableActorsData(
        query: String
    ): Either<Throwable, PagedResponse<Person>> {
        return requestHandler.getPagedResponse<Person>(
            URL("${BASE_URL}search/person?$API_KEY&query=${query.toEncodedQuery()}")
        )
    }

    // search/movie?api_key=API_KEY&query=$thor
    override suspend fun getSearchableMoviesData(
        query: String
    ): Either<Throwable, PagedResponse<Movie>> {
        return requestHandler.getPagedResponse<Movie>(
            URL("${BASE_URL}search/movie?$API_KEY&query=${query.toEncodedQuery()}")
        )
    }
}