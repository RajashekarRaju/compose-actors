package com.developersbreach.composeactors.data.watchlist.repository

import androidx.paging.PagingData
import arrow.core.Either
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {
    suspend fun getAllMovies(): Flow<PagingData<Movie>>

    suspend fun addMovieToWatchlist(
        movieDetail: MovieDetail,
    ): Either<Throwable, Unit>

    suspend fun removeMovieFromWatchlist(
        movie: Movie,
    ): Either<Throwable, Unit>

    suspend fun checkIfMovieIsInWatchlist(
        movieId: Int,
    ): Either<Throwable, Flow<Boolean>>
}