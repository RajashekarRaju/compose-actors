package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.data.datasource.database.AppDatabase
import com.developersbreach.composeactors.data.datasource.database.dao.FavoriteActorsDao
import com.developersbreach.composeactors.data.datasource.database.dao.FavoriteMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    @JvmStatic
    fun providesFavoriteActorsDao(
        database: AppDatabase
    ): FavoriteActorsDao {
        return database.favoriteActorsDao
    }

    @Provides
    @JvmStatic
    fun providesFavoriteMoviesDao(
        database: AppDatabase
    ): FavoriteMoviesDao {
        return database.favoriteMoviesDao
    }
}