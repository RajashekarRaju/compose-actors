package com.developersbreach.composeactors.data.movie.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.movie.model.CastResponse
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.MovieProvidersResponse

interface MovieApi {
    suspend fun getNowPlayingMovies(page: Int): Either<Throwable, PagedResponse<Movie>>

    suspend fun getUpcomingMovies(page: Int): Either<Throwable, PagedResponse<Movie>>

    suspend fun getMovieDetails(movieId: Int): Either<Throwable, MovieDetail>

    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int = 1,
    ): Either<Throwable, PagedResponse<Movie>>

    suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int = 1,
    ): Either<Throwable, PagedResponse<Movie>>

    suspend fun getMovieCast(movieId: Int): Either<Throwable, CastResponse>

    suspend fun getMovieProviders(movieId: Int): Either<Throwable, MovieProvidersResponse>
}