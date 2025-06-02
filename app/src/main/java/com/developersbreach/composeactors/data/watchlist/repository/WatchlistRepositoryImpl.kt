package com.developersbreach.composeactors.data.watchlist.repository

import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.toWatchlistMovie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistMovie
import com.developersbreach.composeactors.data.watchlist.remote.WatchlistApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class WatchlistRepositoryImpl @Inject constructor(
    private val watchlistApi: WatchlistApi,
) : WatchlistRepository {

    override suspend fun getAllMovies(
        page: Int,
    ): Either<Throwable, PagedResponse<WatchlistMovie>> {
        return withContext(Dispatchers.IO) {
            watchlistApi.getAllMovies(page)
        }
    }

    override suspend fun addMovieToWatchlist(
        movieDetail: MovieDetail,
    ): Either<Throwable, Unit> {
        return withContext(Dispatchers.IO) {
            watchlistApi.addMovieToWatchlist(movieDetail.toWatchlistMovie())
        }
    }

    override suspend fun removeMovieFromWatchlist(
        movieId: Int,
    ): Either<Throwable, Unit> {
        return withContext(Dispatchers.IO) {
            watchlistApi.removeMovieFromWatchlist(movieId)
        }
    }
}