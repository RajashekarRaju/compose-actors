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

    // Contains all url endpoints to json data
    private val urls by lazy { Urls }

    // Returns all json responses
    private val jsonData by lazy { JsonRemoteData(urls) }

    // To make http request calls
    private val queryUtils by lazy { NetworkQueryUtils() }

    /** @return list of all popular actors. */
    fun getPopularActors(): List<Actor> {
        val requestUrl = urls.getPopularActorsUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }

    /** @return list of all trending actors. */
    fun getTrendingActors(): List<Actor> {
        val requestUrl = urls.getTrendingActorsUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }

    /** @return Details of user selected actor with */

    /**
     * @param actorId user selected actor.
     * @return details of actor which needs to be fetched.
     */
    fun getActorDetails(
        actorId: Int
    ): ActorDetail {
        val requestUrl = urls.getActorDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorDetailsJsonData(response)
    }

    /**
     * @param actorId user selected actor.
     * @return list of movies which actor was casted with.
     */
    fun getCastDetails(
        actorId: Int
    ): List<Movie> {
        val requestUrl = urls.getCastDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchCastDetailsJsonData(response)
    }

    /**
     * @param query user submitted query to search actors.
     * @return list of actors with matching query.
     */
    fun getSearchableActors(
        query: String
    ): List<Actor> {
        val requestUrl = urls.getSearchActorsUrl(query)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }
}