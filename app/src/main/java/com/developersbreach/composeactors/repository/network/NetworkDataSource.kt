package com.developersbreach.composeactors.repository.network

import com.developersbreach.composeactors.model.*
import com.developersbreach.composeactors.utils.NetworkQueryUtils

/**
 * Class contains functions which can fetch data from network.
 * This is the only data source for whole app.
 */
class NetworkDataSource {

    // Contains all url endpoints to json data
    private val requestUrls by lazy { RequestUrls }

    // Returns all json responses
    private val jsonData by lazy { JsonRemoteData(requestUrls) }

    // To make http request calls
    private val queryUtils by lazy { NetworkQueryUtils() }

    /**
     * @return the result of latest list of all popular actors fetched from the network.
     */
    fun getPopularActors(): List<Actor> {
        val requestUrl = RequestUrls.getPopularActorsUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }

    /** @return the result of latest list of all trending actors fetched from the network. */
    fun getTrendingActors(): List<Actor> {
        val requestUrl = RequestUrls.getTrendingActorsUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }

    /**
     * @param actorId user selected actor.
     * @return the result of details of actor which fetched from the network.
     */
    fun getActorDetails(
        actorId: Int
    ): ActorDetail {
        val requestUrl = RequestUrls.getActorDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorDetailsJsonData(response)
    }

    /**
     * @param actorId user selected actor.
     * @return the result of list of movies which actor was casted with from the network.
     */
    fun getCastDetails(
        actorId: Int
    ): List<Movie> {
        val requestUrl = RequestUrls.getCastDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchCastDetailsJsonData(response)
    }

    /**
     * @param query user submitted query to search actors.
     * @return the result of list of actors with matching query fetched from the network.
     */
    fun getSearchableActors(
        query: String
    ): List<Actor> {
        val requestUrl = RequestUrls.getSearchActorsUrl(query)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchActorsJsonData(response)
    }

    fun getMovieDetailsById(
        movieId: Int
    ): MovieDetail {
        val requestUrl = RequestUrls.getMovieDetailsUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchMovieDetailsJsonData(response)
    }

    /**
     * @param movieId for finding similar movies.
     * @return the result of list of movies which are based on current movie id.
     */
    fun getSimilarMoviesById(
        movieId: Int
    ): List<Movie> {
        val requestUrl = RequestUrls.getSimilarMoviesUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchSimilarAndRecommendedMoviesJsonData(response)
    }

    /**
     * @param movieId for finding recommended movies.
     * @return the result of list of movies which are based on current movie id.
     */
    fun getRecommendedMoviesById(
        movieId: Int
    ): List<Movie> {
        val requestUrl = RequestUrls.getRecommendedMoviesUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchSimilarAndRecommendedMoviesJsonData(response)
    }

    /**
     * @param movieId for finding cast & crew.
     * @return the result of list of cast which are based on current movie id.
     */
    fun getMovieCastById(
        movieId: Int
    ): List<Cast> {
        val requestUrl = RequestUrls.getMovieCastUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchMovieCastByIdJsonData(response)
    }

    fun getUpcomingMovies(): List<Movie> {
        val requestUrl = RequestUrls.getUpcomingMoviesUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchUpcomingMoviesJsonData(response)
    }

    fun getNowPlayingMovies(): List<Movie> {
        val requestUrl = RequestUrls.getNowPlayingMoviesUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        return jsonData.fetchNowPlayingMoviesJsonData(response)
    }
}