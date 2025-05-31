package com.developersbreach.composeactors.data.watchlist.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.BaseUrlProvider.ComposeActorsConfig.BASE_URL
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.watchlist.model.WatchlistMovie
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler,
) : WatchlistApi, BaseUrlProvider() {

    override suspend fun getAllMovies(
        page: Int,
    ): Either<Throwable, PagedResponse<WatchlistMovie>> {
        return requestHandler.getPagedResponse<WatchlistMovie>(
            URL("${BASE_URL}/watchlist/movies?page=$page"),
        )
    }

    override suspend fun addMovieToWatchlist(
        watchlistMovie: WatchlistMovie,
    ): Either<Throwable, Unit> {
        return requestHandler.postResponse<WatchlistMovie>(
            url = URL("$BASE_URL/watchlist/movies"),
            body = watchlistMovie,
        )
    }

    override suspend fun removeMovieFromWatchlist(
        movieId: Int,
    ): Either<Throwable, Unit> {
        return requestHandler.deleteResponse(
            url = URL("$BASE_URL/watchlist/movies/$movieId"),
        )
    }
}