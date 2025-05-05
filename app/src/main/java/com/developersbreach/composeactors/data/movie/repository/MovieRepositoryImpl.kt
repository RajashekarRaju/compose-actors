package com.developersbreach.composeactors.data.movie.repository

import androidx.lifecycle.LiveData
import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.remote.MovieApi
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val databaseDataSource: DatabaseDataSource,
) : MovieRepository {

    override suspend fun getNowPlayingMovies(
        page: Int,
    ): Either<Throwable, PagedResponse<Movie>> = withContext(Dispatchers.IO) {
        movieApi.getNowPlayingMovies(page)
    }

    override suspend fun getUpcomingMovies(
        page: Int,
    ): Either<Throwable, List<Movie>> = withContext(Dispatchers.IO) {
        movieApi.getUpcomingMovies(page).map {
            it.data
        }
    }

    override suspend fun getMovieDetails(
        movieId: Int,
    ): Either<Throwable, MovieDetail> = withContext(Dispatchers.IO) {
        movieApi.getMovieDetails(movieId)
    }

    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int,
    ): Either<Throwable, List<Movie>> = withContext(Dispatchers.IO) {
        movieApi.getSimilarMovies(movieId, page).map {
            it.data
        }
    }

    override suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int,
    ): Either<Throwable, List<Movie>> = withContext(Dispatchers.IO) {
        movieApi.getRecommendedMovies(movieId, page).map {
            it.data
        }
    }

    override suspend fun getMovieCast(
        movieId: Int,
    ): Either<Throwable, List<Cast>> = withContext(Dispatchers.IO) {
        movieApi.getMovieCast(movieId).map {
            it.cast
        }
    }

    override suspend fun getMovieProviders(
        movieId: Int,
    ): Either<Throwable, List<Flatrate>> = withContext(Dispatchers.IO) {
        val response = movieApi.getMovieProviders(movieId)
        response.map {
            val countryCode = Locale.getDefault().country
            it.results[countryCode]?.flatrate ?: emptyList()
        }
    }

    override fun getAllMoviesFromWatchlist(): LiveData<List<Movie>> {
        return databaseDataSource.getAllMoviesFromWatchlist()
    }

    override fun isMovieInWatchlist(movieId: Int): LiveData<Int> {
        return databaseDataSource.checkIfMovieIsInWatchlist(movieId)
    }

    override suspend fun addMovieToWatchlist(movie: Movie) {
        return databaseDataSource.addMovieToWatchlist(movie)
    }

    override suspend fun deleteSelectedMovieFromWatchlist(movie: Movie) {
        return databaseDataSource.deleteSelectedMovieFromWatchlist(movie)
    }
}