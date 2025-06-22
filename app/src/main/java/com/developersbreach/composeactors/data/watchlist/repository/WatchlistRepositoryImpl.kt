package com.developersbreach.composeactors.data.watchlist.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.Either
import arrow.core.raise.either
import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.toWatchlistMovie
import com.developersbreach.composeactors.data.movie.model.toWatchlistMovieEntity
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.model.toWatchlistPerson
import com.developersbreach.composeactors.data.person.model.toWatchlistPersonEntity
import com.developersbreach.composeactors.data.watchlist.cache.toMovie
import com.developersbreach.composeactors.data.watchlist.cache.toWatchlistPerson
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import com.developersbreach.composeactors.data.watchlist.paging.WatchlistMoviesRemoteMediator
import com.developersbreach.composeactors.data.watchlist.paging.WatchlistMoviesRemoteMediator.Companion.WATCH_LIST_PAGE_SIZE
import com.developersbreach.composeactors.data.watchlist.paging.WatchlistPeopleRemoteMediator
import com.developersbreach.composeactors.data.watchlist.remote.WatchlistApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Singleton
class WatchlistRepositoryImpl @Inject constructor(
    private val watchlistApi: WatchlistApi,
    private val watchlistMoviesRemoteMediator: WatchlistMoviesRemoteMediator,
    private val watchlistPeopleRemoteMediator: WatchlistPeopleRemoteMediator,
    private val database: AppDatabase,
    private val authenticationService: AuthenticationService,
) : WatchlistRepository {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getAllMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = WATCH_LIST_PAGE_SIZE,
                initialLoadSize = WATCH_LIST_PAGE_SIZE * 2,
                enablePlaceholders = false,
                prefetchDistance = 1,
            ),
            remoteMediator = if (authenticationService.isGuestUser()) null else watchlistMoviesRemoteMediator,
            pagingSourceFactory = { database.watchlistMoviesDao.getAllMoviesFromWatchlist() },
        ).flow.map { paging ->
            paging.map { it.toMovie() }
        }
    }

    override suspend fun addMovieToWatchlist(
        movieDetail: MovieDetail,
    ): Either<Throwable, Unit> {
        return either {
            withContext(Dispatchers.IO) {
                watchlistApi.addMovieToWatchlist(
                    watchlistMovie = movieDetail.toWatchlistMovie(),
                ).map {
                    database.watchlistMoviesDao.addMovieToWatchlist(movieDetail.toWatchlistMovieEntity())
                }
            }
        }
    }

    override suspend fun removeMovieFromWatchlist(
        movie: Movie,
    ): Either<Throwable, Unit> {
        return either {
            withContext(Dispatchers.IO) {
                watchlistApi.removeMovieFromWatchlist(
                    movieId = movie.movieId,
                ).map {
                    database.watchlistMoviesDao.deleteMovieFromWatchlist(movie.toWatchlistMovieEntity())
                }
            }
        }
    }

    override suspend fun checkIfMovieIsInWatchlist(
        movieId: Int,
    ): Either<Throwable, Flow<Boolean>> {
        return either {
            withContext(Dispatchers.IO) {
                database.watchlistMoviesDao.isMovieInWatchlist(movieId)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPeople(): Flow<PagingData<WatchlistPerson>> {
        return Pager(
            config = PagingConfig(
                pageSize = WATCH_LIST_PAGE_SIZE,
                initialLoadSize = WATCH_LIST_PAGE_SIZE * 2,
                enablePlaceholders = false,
                prefetchDistance = 1,
            ),
            remoteMediator = if (authenticationService.isGuestUser()) null else watchlistPeopleRemoteMediator,
            pagingSourceFactory = { database.watchlistPersonsDao.getPeopleFromWatchlist() },
        ).flow.map { paging ->
            paging.map { it.toWatchlistPerson() }
        }
    }

    override suspend fun addPersonToWatchlist(
        personDetail: PersonDetail,
    ): Either<Throwable, Unit> {
        return either {
            withContext(Dispatchers.IO) {
                watchlistApi.addPersonToWatchlist(
                    watchlistPerson = personDetail.toWatchlistPerson(),
                ).map {
                    database.watchlistPersonsDao.addPersonToWatchlist(personDetail.toWatchlistPersonEntity())
                }
            }
        }
    }

    override suspend fun removePersonFromWatchlist(
        personId: Int,
    ): Either<Throwable, Unit> {
        return either {
            withContext(Dispatchers.IO) {
                watchlistApi.removeMovieFromWatchlist(
                    movieId = personId,
                ).map {
                    database.watchlistPersonsDao.deletePersonFromWatchlist(personId)
                }
            }
        }
    }

    override suspend fun checkIfPersonIsInWatchlist(
        personId: Int,
    ): Either<Throwable, Flow<Boolean>> {
        return either {
            withContext(Dispatchers.IO) {
                database.watchlistPersonsDao.isPersonInWatchlist(personId)
            }
        }
    }
}