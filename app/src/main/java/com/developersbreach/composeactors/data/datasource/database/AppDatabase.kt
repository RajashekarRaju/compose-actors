package com.developersbreach.composeactors.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developersbreach.composeactors.data.datasource.database.dao.FavoriteActorsDao
import com.developersbreach.composeactors.data.datasource.database.dao.FavoriteMoviesDao
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteActorsEntity
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteMoviesEntity


@Database(
    entities = [FavoriteActorsEntity::class, FavoriteMoviesEntity::class],
    version = 4,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteActorsDao: FavoriteActorsDao
    abstract val favoriteMoviesDao: FavoriteMoviesDao
}