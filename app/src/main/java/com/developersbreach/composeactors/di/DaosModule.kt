package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistPersonsDao
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesDao
import com.developersbreach.composeactors.core.database.dao.PersonDetailsDao
import com.developersbreach.composeactors.core.database.dao.SessionsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    @JvmStatic
    fun providesWatchlistPersonsDao(
        database: AppDatabase,
    ): WatchlistPersonsDao {
        return database.watchlistPersonsDao
    }

    @Provides
    @JvmStatic
    fun providesWatchlistMoviesDao(
        database: AppDatabase,
    ): WatchlistMoviesDao {
        return database.watchlistMoviesDao
    }

    @Provides
    @JvmStatic
    fun providesPersonDetailsDao(
        database: AppDatabase,
    ): PersonDetailsDao {
        return database.personDetailsDao
    }

    @Provides
    @JvmStatic
    fun providesSessionDao(
        database: AppDatabase,
    ): SessionsDao {
        return database.sessionsDao
    }
}