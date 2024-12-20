package com.developersbreach.composeactors.data.datasource.network

import com.developersbreach.composeactors.data.PagedResponse
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.Cast
import com.developersbreach.composeactors.data.model.CastResponse
import com.developersbreach.composeactors.data.model.Flatrate
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.MovieDetail
import com.developersbreach.composeactors.data.model.MovieProvidersResponse
import com.developersbreach.composeactors.data.model.MoviesResponse
import com.developersbreach.composeactors.utils.HttpRequestHandler
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Functions in this repository executes on an IO-optimized thread pool, makes main-safe.
 * Repository class executes network calls from different class functions to return data.
 * Data returned from this functions will be exposed to ViewModels.
 *
 * Class contains functions which can fetch data from network.
 * This is the only network data source for whole app.
 */
@Singleton
class NetworkDataSource @Inject constructor(
    private val requestUrls: RequestUrls,
    private val requestHandler: HttpRequestHandler
) {

    /*** @return the result of latest list of all popular actors fetched from the network.*/
    suspend fun getPopularActorsData(): List<Actor> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getPopularActorsUrl()
        requestHandler.getPagedResponse<Actor>(requestUrl).data
    }

    /** @return the result of latest list of all trending actors fetched from the network. */
    suspend fun getTrendingActorsData(): List<Actor> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getTrendingActorsUrl()
        requestHandler.getPagedResponse<Actor>(requestUrl).data
    }

    /**
     * @param actorId user selected actor.
     * @return the result of details of actor which fetched from the network.
     */
    suspend fun getSelectedActorData(
        actorId: Int
    ): ActorDetail = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getActorDetailsUrl(actorId)
        requestHandler.getResponse(requestUrl)
    }

    /**
     * @param actorId user selected actor.
     * @return the result of list of movies which actor was casted with from the network.
     */
    suspend fun getCastData(
        actorId: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getCastDetailsUrl(actorId)
        requestHandler.getResponse<MoviesResponse>(requestUrl).movies
    }

    suspend fun getSelectedMovieData(
        movieId: Int
    ): MovieDetail = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getMovieDetailsUrl(movieId)
        requestHandler.getResponse(requestUrl)
    }

    /**
     * @param movieId for finding similar movies.
     * @return the result of list of movies which are based on current movie id.
     */
    suspend fun getSimilarMoviesByIdData(
        movieId: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getSimilarMoviesUrl(movieId)
        requestHandler.getPagedResponse<Movie>(requestUrl).data
    }

    /**
     * @param movieId for finding recommended movies.
     * @return the result of list of movies which are based on current movie id.
     */
    suspend fun getRecommendedMoviesByIdData(
        movieId: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getRecommendedMoviesUrl(movieId)
        requestHandler.getPagedResponse<Movie>(requestUrl).data
    }

    /**
     * @param movieId for finding cast & crew.
     * @return the result of list of cast which are based on current movie id.
     */
    suspend fun getMovieCastByIdData(
        movieId: Int
    ): List<Cast> {
        return withContext(Dispatchers.IO) {
            val requestUrl = requestUrls.getMovieCastUrl(movieId)
            requestHandler.getResponse<CastResponse>(requestUrl).cast
        }
    }

    suspend fun getUpcomingMoviesData(): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getUpcomingMoviesUrl()
        requestHandler.getPagedResponse<Movie>(requestUrl).data
    }

    suspend fun getMovieProvidersData(
        movieId: Int
    ): List<Flatrate> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getMovieProviderUrl(movieId)
        val response: MovieProvidersResponse = requestHandler.getResponse(requestUrl)
        val countryCode = Locale.getDefault().country
        response.results[countryCode]?.flatrate ?: emptyList()
    }

    suspend fun getNowPlayingMoviesData(
        page: Int
    ): PagedResponse<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getNowPlayingMoviesUrl(page)
        requestHandler.getResponse(requestUrl)
    }

    /**
     * @param query user submitted query to search actors.
     * @return the result of list of actors with matching query fetched from the network.
     */
    suspend fun getSearchableActorsData(
        query: String
    ): List<Actor> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getSearchActorsUrl(query)
        requestHandler.getPagedResponse<Actor>(requestUrl).data
    }

    suspend fun getSearchableMoviesData(
        query: String
    ): List<Movie> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getSearchMoviesUrl(query)
        requestHandler.getPagedResponse<Movie>(requestUrl).data
    }

    suspend fun getMovieTrailerUrl(
        movieId: Int
    ) {
        withContext(Dispatchers.IO) {
            val requestUrl = requestUrls.getMovieTrailerUrl(movieId)
            // val response = requestHandler.getResponseFromHttpUrl(requestUrl)

            // Watching trailer for movies needs to implemented
            // Planned for v0.5.0
        }
    }

    suspend fun getMovieTrailerThumbnail(
        trailerId: String
    ) {
        withContext(Dispatchers.IO) {
            val requestUrl = requestUrls.buildMovieTrailerThumbnail(trailerId)
            // val response = requestHandler.getResponseFromHttpUrl(requestUrl)

            // Watching trailer for movies needs to implemented
            // Planned for v0.5.0
        }
    }
}