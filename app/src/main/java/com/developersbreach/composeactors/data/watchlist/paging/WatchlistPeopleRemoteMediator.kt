package com.developersbreach.composeactors.data.watchlist.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistPersonEntity
import com.developersbreach.composeactors.data.watchlist.cache.toWatchlistPeopleEntity
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistPersonRemoteKeysEntity
import com.developersbreach.composeactors.data.watchlist.remote.WatchlistApi
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class WatchlistPeopleRemoteMediator @Inject constructor(
    private val watchlistApi: WatchlistApi,
    private val database: AppDatabase,
) : RemoteMediator<Int, WatchlistPersonEntity>() {

    override suspend fun initialize(): InitializeAction {
        return if (database.watchlistPersonsDao.existsAny()) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WatchlistPersonEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.pages
                    .flatMap { it.data }
                    .lastOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = false)

                val remoteKeys = database.watchlistPeopleRemoteKeysDao
                    .remoteKeysByPersonId(lastItem.personId)
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                remoteKeys.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            watchlistApi.getPeople(
                page = page,
                size = WATCH_LIST_PAGE_SIZE,
            ).fold(
                ifLeft = { MediatorResult.Error(it) },
                ifRight = { response ->
                    val people = response.data
                    val endReached = page >= response.totalPages - 1

                    database.withTransaction {
                        if (loadType == LoadType.REFRESH && database.watchlistPersonsDao.existsAny()) {
                            database.watchlistPersonsDao.deletePeopleFromWatchlist()
                            database.watchlistPeopleRemoteKeysDao.clearRemoteKeys()
                        }

                        val keys = people.map { person ->
                            WatchlistPersonRemoteKeysEntity(
                                personId = person.personId,
                                prevKey = page.takeIf { it > 0 }?.minus(1),
                                nextKey = if (endReached) null else page + 1,
                            )
                        }
                        database.watchlistPeopleRemoteKeysDao.insertAll(keys)
                        database.watchlistPersonsDao.addPeopleToWatchlist(people.toWatchlistPeopleEntity())
                    }

                    MediatorResult.Success(endOfPaginationReached = endReached)
                },
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val WATCH_LIST_PAGE_SIZE = 5
    }
}