package com.developersbreach.composeactors.data.repository.movie

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.data.model.Cast
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.MovieDetail
import javax.inject.Singleton
import com.developersbreach.composeactors.data.repository.DatabaseRepository
import com.developersbreach.composeactors.data.repository.NetworkRepository
import javax.inject.Inject

@Singleton
class MovieRepository @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) {

    suspend fun getSelectedMovieData(movieId: Int): MovieDetail {
        return networkRepository.getSelectedMovieData(movieId)
    }

    suspend fun getSimilarMoviesByIdData(movieId: Int): List<Movie> {
        return networkRepository.getSimilarMoviesByIdData(movieId)
    }

    suspend fun getRecommendedMoviesByIdData(movieId: Int): List<Movie> {
        return networkRepository.getRecommendedMoviesByIdData(movieId)
    }

    suspend fun getMovieCastByIdData(movieId: Int): List<Cast> {
        return networkRepository.getMovieCastByIdData(movieId)
    }

    fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        return databaseRepository.getAllFavoriteMovies()
    }

    fun isFavoriteMovie(movieId: Int): LiveData<Int> {
        return databaseRepository.checkIfMovieIsFavorite(movieId)
    }

    suspend fun addMovieToFavorites(movie: Movie) {
        return databaseRepository.addMovieToFavorites(movie)
    }

    suspend fun deleteSelectedFavoriteMovie(movie: Movie) {
        return databaseRepository.deleteSelectedFavoriteMovie(movie)
    }
}