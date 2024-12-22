package com.developersbreach.composeactors.data.movie.remote

import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.model.CastResponse
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.MovieDetail
import com.developersbreach.composeactors.data.model.MovieProvidersResponse

interface MovieApi {
    suspend fun getNowPlayingMovies(page: Int): PagedResponse<Movie>
    suspend fun getUpcomingMovies(page: Int): PagedResponse<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetail
    suspend fun getSimilarMovies(
        movieId: Int,
        page: Int = 1,
    ): PagedResponse<Movie>
    suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int = 1,
    ): PagedResponse<Movie>
    suspend fun getMovieCast(movieId: Int): CastResponse
    suspend fun getMovieProviders(movieId: Int): MovieProvidersResponse
}