package com.developersbreach.composeactors.data

import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.utils.NetworkQueryUtils


/**
 * Builds Uri used to fetch data from the server.
 * @return The String to use to query the data from the server.
 */
class NetworkService {

    private val jsonData by lazy { JsonRemoteData() }
    private val queryUtils by lazy { NetworkQueryUtils() }
    private val urls by lazy { Urls }

    // Returns new URL object from the given string URL.
    fun getPopularActors(): List<Actor> {
        val requestUrl = urls.getPopularActorsUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }

    fun getTrendingActors(): List<Actor> {
        val requestUrl = urls.getTrendingActorsUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }

    fun getActorDetails(
        actorId: Int
    ): ActorDetail {
        val requestUrl = urls.getActorDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorDetailsJsonData(response)
    }

    //https://api.themoviedb.org/3/person/3223/movie_credits?api_key=
    fun getCastDetails(
        actorId: Int
    ): List<Movie> {
        val requestUrl = urls.getCastDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchCastDetailsJsonData(response)
    }

    // https://api.themoviedb.org/3/search/person?api_key=&query=
    fun getSearchableActors(
        query: String
    ): List<Actor> {
        val requestUrl = urls.getSearchActorsUrl(query)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }
}