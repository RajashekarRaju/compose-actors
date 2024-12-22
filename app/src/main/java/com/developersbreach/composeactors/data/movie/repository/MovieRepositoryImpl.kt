package com.developersbreach.composeactors.data.movie.repository

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.MovieProvidersResponse
import com.developersbreach.composeactors.data.movie.remote.MovieApi
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val databaseDataSource: DatabaseDataSource
) : MovieRepository {

    override suspend fun getNowPlayingMovies(
        page: Int
    ): PagedResponse<Movie> = withContext(Dispatchers.IO) {
        movieApi.getNowPlayingMovies(page)
    }

    override suspend fun getUpcomingMovies(
        page: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        movieApi.getUpcomingMovies(page).data
    }

    override suspend fun getMovieDetails(
        movieId: Int
    ): MovieDetail = withContext(Dispatchers.IO) {
        movieApi.getMovieDetails(movieId)
    }

    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        movieApi.getSimilarMovies(movieId, page).data
    }

    override suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int
    ): List<Movie> = withContext(Dispatchers.IO) {
        movieApi.getRecommendedMovies(movieId, page).data
    }

    override suspend fun getMovieCast(
        movieId: Int
    ): List<Cast> = withContext(Dispatchers.IO) {
        movieApi.getMovieCast(movieId).cast
    }

    override suspend fun getMovieProviders(
        movieId: Int
    ): List<Flatrate> = withContext(Dispatchers.IO) {
        val response: MovieProvidersResponse = movieApi.getMovieProviders(movieId)
        val countryCode = Locale.getDefault().country
        response.results[countryCode]?.flatrate ?: emptyList()
    }

    override fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        return databaseDataSource.getAllFavoriteMovies()
    }

    override fun isFavoriteMovie(movieId: Int): LiveData<Int> {
        return databaseDataSource.checkIfMovieIsFavorite(movieId)
    }

    override suspend fun addMovieToFavorites(movie: Movie) {
        return databaseDataSource.addMovieToFavorites(movie)
    }

    override suspend fun deleteSelectedFavoriteMovie(movie: Movie) {
        return databaseDataSource.deleteSelectedFavoriteMovie(movie)
    }
}