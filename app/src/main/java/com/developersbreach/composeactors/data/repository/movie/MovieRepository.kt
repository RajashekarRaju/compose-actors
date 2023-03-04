package com.developersbreach.composeactors.data.repository.movie

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.data.PagedResponse
import com.developersbreach.composeactors.data.model.Cast
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.MovieDetail
import javax.inject.Singleton
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.datasource.network.NetworkDataSource
import javax.inject.Inject

@Singleton
class MovieRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource
) {

    suspend fun getNowPlayingMoviesData(page: Int): PagedResponse<Movie> {
        return networkDataSource.getNowPlayingMoviesData(page)
    }

    suspend fun getSelectedMovieData(movieId: Int): MovieDetail {
        return networkDataSource.getSelectedMovieData(movieId)
    }

    suspend fun getSimilarMoviesByIdData(movieId: Int): List<Movie> {
        return networkDataSource.getSimilarMoviesByIdData(movieId)
    }

    suspend fun getRecommendedMoviesByIdData(movieId: Int): List<Movie> {
        return networkDataSource.getRecommendedMoviesByIdData(movieId)
    }

    suspend fun getMovieCastByIdData(movieId: Int): List<Cast> {
        return networkDataSource.getMovieCastByIdData(movieId)
    }

    fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        return databaseDataSource.getAllFavoriteMovies()
    }

    fun isFavoriteMovie(movieId: Int): LiveData<Int> {
        return databaseDataSource.checkIfMovieIsFavorite(movieId)
    }

    suspend fun addMovieToFavorites(movie: Movie) {
        return databaseDataSource.addMovieToFavorites(movie)
    }

    suspend fun deleteSelectedFavoriteMovie(movie: Movie) {
        return databaseDataSource.deleteSelectedFavoriteMovie(movie)
    }
}