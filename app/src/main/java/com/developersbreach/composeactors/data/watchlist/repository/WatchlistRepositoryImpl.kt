package com.developersbreach.composeactors.data.watchlist.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.Either
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

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
        return Either.catch {
            withContext(Dispatchers.IO) {
                // Only make API call if not a guest user
                if (!authenticationService.isGuestUser()) {
                    watchlistApi.addMovieToWatchlist(
                        watchlistMovie = movieDetail.toWatchlistMovie(),
                    ).fold(
                        ifLeft = { throw it },
                        ifRight = { Timber.d("Success addMovieToWatchlist $it") },
                    )
                }
                // Always update local database
                database.watchlistMoviesDao.addMovieToWatchlist(movieDetail.toWatchlistMovieEntity())
            }
        }
    }

    override suspend fun removeMovieFromWatchlist(
        movie: Movie,
    ): Either<Throwable, Unit> {
        return Either.catch {
            withContext(Dispatchers.IO) {
                // Only make API call if not a guest user
                if (!authenticationService.isGuestUser()) {
                    watchlistApi.removeMovieFromWatchlist(
                        movieId = movie.movieId,
                    ).fold(
                        ifLeft = { throw it },
                        ifRight = { Timber.d("Success removeMovieFromWatchlist $it") },
                    )
                }
                // Always update local database
                database.watchlistMoviesDao.deleteMovieFromWatchlist(movie.toWatchlistMovieEntity())
            }
        }
    }

    override suspend fun checkIfMovieIsInWatchlist(
        movieId: Int,
    ): Either<Throwable, Flow<Boolean>> {
        return Either.catch {
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
        return Either.catch {
            withContext(Dispatchers.IO) {
                // Only make API call if not a guest user
                if (!authenticationService.isGuestUser()) {
                    watchlistApi.addPersonToWatchlist(
                        watchlistPerson = personDetail.toWatchlistPerson(),
                    ).fold(
                        ifLeft = { throw it },
                        ifRight = { Timber.d("Success addPersonToWatchlist $it") },
                    )
                }
                // Always update local database
                database.watchlistPersonsDao.addPersonToWatchlist(personDetail.toWatchlistPersonEntity())
            }
        }
    }

    override suspend fun removePersonFromWatchlist(
        personId: Int,
    ): Either<Throwable, Unit> {
        return Either.catch {
            withContext(Dispatchers.IO) {
                // Only make API call if not a guest user
                if (!authenticationService.isGuestUser()) {
                    watchlistApi.removeMovieFromWatchlist(
                        movieId = personId,
                    ).fold(
                        ifLeft = { throw it },
                        ifRight = { Timber.d("Success removePersonFromWatchlist $it") },
                    )
                }
                // Always update local database
                database.watchlistPersonsDao.deletePersonFromWatchlist(personId)
            }
        }
    }

    override suspend fun checkIfPersonIsInWatchlist(
        personId: Int,
    ): Either<Throwable, Flow<Boolean>> {
        return Either.catch {
            withContext(Dispatchers.IO) {
                database.watchlistPersonsDao.isPersonInWatchlist(personId)
            }
        }
    }

    override suspend fun clearWatchlistData() {
        withContext(Dispatchers.IO) {
            database.watchlistMoviesDao.deleteAllMoviesInWatchlist()
            database.watchlistPersonsDao.deletePeopleFromWatchlist()
            database.watchlistMoviesRemoteKeysDao.clearRemoteKeys()
            database.watchlistPeopleRemoteKeysDao.clearRemoteKeys()
        }
    }
}