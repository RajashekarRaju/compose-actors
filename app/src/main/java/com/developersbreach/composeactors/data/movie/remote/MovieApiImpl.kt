package com.developersbreach.composeactors.data.movie.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.movie.model.CastResponse
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.MovieProvidersResponse
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler
) : MovieApi, BaseUrlProvider() {

    // movie/now_playing?api_key=API_KEY&page=1
    override suspend fun getNowPlayingMovies(
        page: Int
    ): Either<Throwable, PagedResponse<Movie>> {
        return requestHandler.getPagedResponse<Movie>(
            URL("${BASE_URL}movie/now_playing?$API_KEY&page=$page")
        )
    }

    // movie/upcoming?api_key=API_KEY&page=1
    override suspend fun getUpcomingMovies(
        page: Int
    ): Either<Throwable, PagedResponse<Movie>> {
        return requestHandler.getPagedResponse<Movie>(
            URL("${BASE_URL}movie/upcoming?$API_KEY&page=$page")
        )
    }

    // movie/{movie_id}?api_key=API_KEY
    override suspend fun getMovieDetails(
        movieId: Int
    ): Either<Throwable, MovieDetail> {
        return requestHandler.getResponse<MovieDetail>(
            URL("${BASE_URL}movie/$movieId?$API_KEY")
        )
    }

    // movie/{movie_id}/similar?api_key=API_KEY&page=1
    override suspend fun getSimilarMovies(
        movieId: Int,
        page: Int
    ): Either<Throwable, PagedResponse<Movie>> {
        return requestHandler.getPagedResponse<Movie>(
            URL("${BASE_URL}movie/$movieId/similar?$API_KEY&page=$page")
        )
    }

    // movie/{movie_id}/recommendations?api_key=API_KEY&page=1
    override suspend fun getRecommendedMovies(
        movieId: Int,
        page: Int
    ): Either<Throwable, PagedResponse<Movie>> {
        return requestHandler.getPagedResponse<Movie>(
            URL("${BASE_URL}movie/$movieId/recommendations?$API_KEY&page=$page")
        )
    }

    // movie/{movie_id}/credits?api_key=API_KEY - 299536
    override suspend fun getMovieCast(
        movieId: Int
    ): Either<Throwable, CastResponse> {
        return requestHandler.getResponse<CastResponse>(
            URL("${BASE_URL}movie/$movieId/credits?$API_KEY")
        )
    }

    // movie/{movie_id}/watch/providers?api_key
    override suspend fun getMovieProviders(
        movieId: Int
    ): Either<Throwable, MovieProvidersResponse> {
        return requestHandler.getResponse<MovieProvidersResponse>(
            URL("${BASE_URL}movie/$movieId/watch/providers?$API_KEY")
        )
    }
}