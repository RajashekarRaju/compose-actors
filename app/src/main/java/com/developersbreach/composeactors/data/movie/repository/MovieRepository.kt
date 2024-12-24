package com.developersbreach.composeactors.data.movie.repository

import androidx.lifecycle.LiveData
import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail

interface MovieRepository {
    suspend fun getNowPlayingMovies(page: Int): Either<Throwable, PagedResponse<Movie>>
    suspend fun getUpcomingMovies(page: Int = 1): Either<Throwable, List<Movie>>
    suspend fun getMovieDetails(movieId: Int): Either<Throwable, MovieDetail>
    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int = 1,
    ): Either<Throwable, List<Movie>>
    suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int = 1,
    ): Either<Throwable, List<Movie>>
    suspend fun getMovieCast(movieId: Int): Either<Throwable, List<Cast>>
    suspend fun getMovieProviders(movieId: Int): Either<Throwable, List<Flatrate>>
    fun getAllFavoriteMovies(): LiveData<List<Movie>>
    fun isFavoriteMovie(movieId: Int): LiveData<Int>
    suspend fun addMovieToFavorites(movie: Movie)
    suspend fun deleteSelectedFavoriteMovie(movie: Movie)
}