package com.developersbreach.composeactors.data

import com.developersbreach.composeactors.utils.ApiKey
import java.net.URL


object Urls {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "api_key=${ApiKey.API_KEY}"
    const val LOW_RES_IMAGE = "https://image.tmdb.org/t/p/w200"
    const val HIGH_RES_IMAGE = "https://image.tmdb.org/t/p/w500"

    fun getPopularActorsUrl(): URL {
        return URL("${BASE_URL}person/popular?$API_KEY")
    }

    fun getTrendingActorsUrl(): URL {
        return URL("${BASE_URL}trending/person/week?$API_KEY")
    }

    fun getActorDetailsUrl(actorId: Int): URL {
        return URL("${BASE_URL}person/${actorId}?$API_KEY")
    }

    fun getCastDetailsUrl(actorId: Int): URL {
        return URL("${BASE_URL}person/${actorId}/movie_credits?$API_KEY")
    }

    fun getSearchActorsUrl(query: String): URL {
        return URL("${BASE_URL}search/person?$API_KEY&query=$query")
    }
}