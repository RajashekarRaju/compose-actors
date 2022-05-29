package com.developersbreach.composeactors.di

import android.content.Context
import com.developersbreach.composeactors.repository.database.FavoritesDatabase
import com.developersbreach.composeactors.repository.database.dao.FavoriteActorsDao
import com.developersbreach.composeactors.repository.database.dao.FavoriteMoviesDao
import com.developersbreach.composeactors.repository.database.getDatabaseInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FavoritesDatabase {
        return getDatabaseInstance(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesFavoriteActorsDao(
        database: FavoritesDatabase
    ): FavoriteActorsDao = database.favoriteActorsDao

    @Provides
    fun providesFavoriteMoviesDao(
        database: FavoritesDatabase
    ): FavoriteMoviesDao = database.favoriteMoviesDao
}