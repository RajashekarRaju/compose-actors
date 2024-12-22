package com.developersbreach.composeactors.data.person.remote

import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.MoviesResponse
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler,
) : PersonApi, BaseUrlProvider() {

    // person/popular?api_key=API_KEY
    override suspend fun getPopularPersons(): PagedResponse<Actor> {
        return requestHandler.getPagedResponse(
            URL("${BASE_URL}person/popular?$API_KEY")
        )
    }

    // person/3233?api_key=API_KEY
    override suspend fun getTrendingPersons(): PagedResponse<Actor> {
        return requestHandler.getPagedResponse(
            URL("${BASE_URL}trending/person/week?$API_KEY")
        )
    }

    // person/3233?api_key=API_KEY
    override suspend fun getPersonDetails(
        actorId: Int
    ): ActorDetail {
        return requestHandler.getResponse(
            URL("${BASE_URL}person/${actorId}?$API_KEY")
        )
    }

    // person/3233/movie_credits?api_key=API_KEY
    override suspend fun getCastDetails(
        actorId: Int
    ): MoviesResponse {
        return requestHandler.getResponse<MoviesResponse>(
            URL("${BASE_URL}person/${actorId}/movie_credits?$API_KEY")
        )
    }
}