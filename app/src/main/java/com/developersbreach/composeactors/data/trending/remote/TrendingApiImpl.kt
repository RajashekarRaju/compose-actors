package com.developersbreach.composeactors.data.trending.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.BaseUrlProvider.TmdbConfig.TMDB_API_KEY
import com.developersbreach.composeactors.core.network.BaseUrlProvider.TmdbConfig.TMDB_BASE_URL
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.person.model.Person
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler,
) : TrendingApi, BaseUrlProvider() {

    // trending/person/week?api_key=TMDB_API_KEY
    override suspend fun getTrendingActors(): Either<Throwable, PagedResponse<Person>> {
        return requestHandler.getPagedResponse<Person>(
            URL("${TMDB_BASE_URL}trending/person/week?${TMDB_API_KEY}"),
        )
    }
}