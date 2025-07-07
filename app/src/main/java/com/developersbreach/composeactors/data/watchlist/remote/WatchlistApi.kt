package com.developersbreach.composeactors.data.watchlist.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import com.developersbreach.composeactors.data.watchlist.model.WatchlistMovie

interface WatchlistApi {
    suspend fun getAllMovies(
        page: Int,
        size: Int,
    ): Either<Throwable, PagedResponse<WatchlistMovie>>

    suspend fun addMovieToWatchlist(
        watchlistMovie: WatchlistMovie,
    ): Either<Throwable, Unit>

    suspend fun removeMovieFromWatchlist(
        movieId: Int,
    ): Either<Throwable, Unit>

    suspend fun getPeople(
        page: Int,
        size: Int,
    ): Either<Throwable, PagedResponse<WatchlistPerson>>

    suspend fun addPersonToWatchlist(
        watchlistPerson: WatchlistPerson,
    ): Either<Throwable, Unit>

    suspend fun removePersonFromWatchlist(
        personId: Int,
    ): Either<Throwable, Unit>
}