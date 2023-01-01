package com.developersbreach.composeactors.data.repository

import com.developersbreach.composeactors.data.model.*
import com.developersbreach.composeactors.data.datasource.network.JsonRemoteData
import com.developersbreach.composeactors.data.datasource.network.RequestUrls
import com.developersbreach.composeactors.utils.NetworkQueryUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Functions in this repository executes on an IO-optimized thread pool, makes main-safe.
 * Repository class executes network calls from different class functions to return data.
 * Data returned from this functions will be exposed to ViewModels.
 *
 * Class contains functions which can fetch data from network.
 * This is the only network data source for whole app.
 */
@Singleton
class NetworkRepository @Inject constructor(
    private val requestUrls: RequestUrls,
    private val jsonData: JsonRemoteData,
    private val queryUtils: NetworkQueryUtils
) {

    /*** @return the result of latest list of all popular actors fetched from the network.*/
    suspend fun getPopularActorsData(): List<Actor> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getPopularActorsUrl()
        val response: String = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchActorsJsonData(response)
    }

    /** @return the result of latest list of all trending actors fetched from the network. */
    suspend fun getTrendingActorsData(): List<Actor> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getTrendingActorsUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchActorsJsonData(response)
    }

    /**
     * @param actorId user selected actor.
     * @return the result of details of actor which fetched from the network.
     */
    suspend fun getSelectedActorData(
        actorId: Int
    ): ActorDetail = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getActorDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchActorDetailsJsonData(response)
    }

    /**
     * @param actorId user selected actor.
     * @return the result of list of movies which actor was casted with from the network.
     */
    suspend fun getCastData(
        actorId: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getCastDetailsUrl(actorId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchCastDetailsJsonData(response)
    }

    suspend fun getSelectedMovieData(
        movieId: Int
    ): MovieDetail = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getMovieDetailsUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchMovieDetailsJsonData(response)
    }

    /**
     * @param movieId for finding similar movies.
     * @return the result of list of movies which are based on current movie id.
     */
    suspend fun getSimilarMoviesByIdData(
        movieId: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getSimilarMoviesUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchSimilarAndRecommendedMoviesJsonData(response)
    }

    /**
     * @param movieId for finding recommended movies.
     * @return the result of list of movies which are based on current movie id.
     */
    suspend fun getRecommendedMoviesByIdData(
        movieId: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getRecommendedMoviesUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchSimilarAndRecommendedMoviesJsonData(response)
    }

    /**
     * @param movieId for finding cast & crew.
     * @return the result of list of cast which are based on current movie id.
     */
    suspend fun getMovieCastByIdData(
        movieId: Int
    ): List<Cast> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getMovieCastUrl(movieId)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchMovieCastByIdJsonData(response)
    }

    suspend fun getUpcomingMoviesData(): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getUpcomingMoviesUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchUpcomingMoviesJsonData(response)
    }

    suspend fun getNowPlayingMoviesData(): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getNowPlayingMoviesUrl()
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchNowPlayingMoviesJsonData(response)
    }
}