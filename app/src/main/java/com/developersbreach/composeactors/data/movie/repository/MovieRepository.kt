package com.developersbreach.composeactors.data.movie.repository

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int): PagedResponse<Movie>
    suspend fun getUpcomingMovies(page: Int = 1): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetail
    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int = 1,
    ): List<Movie>
    suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int = 1,
    ): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<Cast>
    suspend fun getMovieProviders(movieId: Int): List<Flatrate>
    fun getAllFavoriteMovies(): LiveData<List<Movie>>
    fun isFavoriteMovie(movieId: Int): LiveData<Int>
    suspend fun addMovieToFavorites(movie: Movie)
    suspend fun deleteSelectedFavoriteMovie(movie: Movie)
}