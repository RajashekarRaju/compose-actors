package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.core.database.dao.FavoritePersonsDao
import com.developersbreach.composeactors.core.database.dao.FavoriteMoviesDao
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
    fun providesFavoritePersonsDao(
        database: AppDatabase,
    ): FavoritePersonsDao {
        return database.favoritePersonsDao
    }

    @Provides
    @JvmStatic
    fun providesFavoriteMoviesDao(
        database: AppDatabase,
    ): FavoriteMoviesDao {
        return database.favoriteMoviesDao
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