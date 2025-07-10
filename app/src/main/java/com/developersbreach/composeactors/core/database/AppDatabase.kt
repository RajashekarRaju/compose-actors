package com.developersbreach.composeactors.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developersbreach.composeactors.core.database.dao.PersonDetailsDao
import com.developersbreach.composeactors.core.database.dao.SessionsDao
import com.developersbreach.composeactors.core.database.dao.RegionDao
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistPersonsDao
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity
import com.developersbreach.composeactors.core.database.entity.SessionEntity
import com.developersbreach.composeactors.core.database.entity.RegionEntity
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistPersonEntity
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMovieEntity
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesDao
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesRemoteKeysDao
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesRemoteKeysEntity
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistPeopleRemoteKeysDao
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistPersonRemoteKeysEntity

@Database(
    entities = [
        WatchlistPersonEntity::class,
        WatchlistMovieEntity::class,
        PersonDetailEntity::class,
        SessionEntity::class,
        RegionEntity::class,
        WatchlistMoviesRemoteKeysEntity::class,
        WatchlistPersonRemoteKeysEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val watchlistPersonsDao: WatchlistPersonsDao
    abstract val personDetailsDao: PersonDetailsDao
    abstract val watchlistMoviesDao: WatchlistMoviesDao
    abstract val sessionsDao: SessionsDao
    abstract val regionDao: RegionDao
    abstract val watchlistMoviesRemoteKeysDao: WatchlistMoviesRemoteKeysDao
    abstract val watchlistPeopleRemoteKeysDao: WatchlistPeopleRemoteKeysDao
}