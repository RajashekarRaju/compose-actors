package com.developersbreach.composeactors.data.watchlist.repository

import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.watchlist.model.WatchlistMovie

interface WatchlistRepository {
    suspend fun getAllMovies(
        page: Int,
    ): Either<Throwable, PagedResponse<WatchlistMovie>>

    suspend fun addMovieToWatchlist(
        movieDetail: MovieDetail,
    ): Either<Throwable, Unit>

    suspend fun removeMovieFromWatchlist(
        movieId: Int,
    ): Either<Throwable, Unit>
}