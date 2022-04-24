package com.developersbreach.composeactors.repository.network

import com.developersbreach.composeactors.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Functions in this repository executes on an IO-optimized thread pool, makes main-safe.
 * Repository class executes network calls from [NetworkDataSource] to return data.
 * Data returned from this functions will be exposed to ViewModels.
 */
class NetworkRepository(
    private val source: NetworkDataSource
) {

    // Suspend function executes network call.
    suspend fun getPopularActorsData(): List<Actor> {
        val popularActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            popularActorsList = source.getPopularActors()
        }
        return popularActorsList
    }

    // Suspend function executes network call.
    suspend fun getTrendingActorsData(): List<Actor> {
        val trendingActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            trendingActorsList = source.getTrendingActors()
        }
        return trendingActorsList
    }

    // Suspend function executes network call.
    suspend fun getSelectedActorData(
        actorId: Int
    ): ActorDetail {
        val selectedActorDetails: ActorDetail
        withContext(Dispatchers.IO) {
            selectedActorDetails = source.getActorDetails(actorId)
        }
        return selectedActorDetails
    }

    // Suspend function executes network call.
    suspend fun getCastData(
        actorId: Int
    ): List<Movie> {
        val castListData: List<Movie>
        withContext(Dispatchers.IO) {
            castListData = source.getCastDetails(actorId)
        }
        return castListData
    }

    // Suspend function executes network call.
    suspend fun getSearchableActorsData(
        query: String
    ): List<Actor> {
        val searchableActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            searchableActorsList = source.getSearchableActors(query)
        }
        return searchableActorsList
    }

    suspend fun getSelectedMovieData(
        movieId: Int
    ): MovieDetail {
        val selectedMovieDetails: MovieDetail
        withContext(Dispatchers.IO) {
            selectedMovieDetails = source.getMovieDetailsById(movieId)
        }
        return selectedMovieDetails
    }

    suspend fun getSimilarMoviesByIdData(
        movieId: Int
    ): List<Movie> {
        val similarMovies: List<Movie>
        withContext(Dispatchers.IO) {
            similarMovies = source.getSimilarMoviesById(movieId)
        }
        return similarMovies
    }

    suspend fun getRecommendedMoviesByIdData(
        movieId: Int
    ): List<Movie> {
        val recommendedMovies: List<Movie>
        withContext(Dispatchers.IO) {
            recommendedMovies = source.getRecommendedMoviesById(movieId)
        }
        return recommendedMovies
    }

    suspend fun getMovieCastByIdData(
        movieId: Int
    ): List<Cast> {
        val castList: List<Cast>
        withContext(Dispatchers.IO) {
            castList = source.getMovieCastById(movieId)
        }
        return castList
    }

    // Suspend function executes network call.
    suspend fun getUpcomingMoviesData(): List<Movie> {
        val upcomingMoviesList: List<Movie>
        withContext(Dispatchers.IO) {
            upcomingMoviesList = source.getUpcomingMovies()
        }
        return upcomingMoviesList
    }

    // Suspend function executes network call.
    suspend fun getNowPlayingMoviesData(): List<Movie> {
        val nowPlayingMoviesList: List<Movie>
        withContext(Dispatchers.IO) {
            nowPlayingMoviesList = source.getNowPlayingMovies()
        }
        return nowPlayingMoviesList
    }
}