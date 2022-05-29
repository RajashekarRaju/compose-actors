package com.developersbreach.composeactors.repository.network

import com.developersbreach.composeactors.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val source: NetworkDataSource,
) {

    suspend fun getPopularActorsData(): List<Actor> {
        val popularActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            popularActorsList = source.getPopularActors()
        }
        return popularActorsList
    }

    suspend fun getTrendingActorsData(): List<Actor> {
        val trendingActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            trendingActorsList = source.getTrendingActors()
        }
        return trendingActorsList
    }

    suspend fun getSelectedActorData(
        actorId: Int
    ): ActorDetail {
        val selectedActorDetails: ActorDetail
        withContext(Dispatchers.IO) {
            selectedActorDetails = source.getActorDetails(actorId)
        }
        return selectedActorDetails
    }

    suspend fun getCastData(
        actorId: Int
    ): List<Movie> {
        val castListData: List<Movie>
        withContext(Dispatchers.IO) {
            castListData = source.getCastDetails(actorId)
        }
        return castListData
    }

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

    suspend fun getUpcomingMoviesData(): List<Movie> {
        val upcomingMoviesList: List<Movie>
        withContext(Dispatchers.IO) {
            upcomingMoviesList = source.getUpcomingMovies()
        }
        return upcomingMoviesList
    }

    suspend fun getNowPlayingMoviesData(): List<Movie> {
        val nowPlayingMoviesList: List<Movie>
        withContext(Dispatchers.IO) {
            nowPlayingMoviesList = source.getNowPlayingMovies()
        }
        return nowPlayingMoviesList
    }
}