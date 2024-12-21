package com.developersbreach.composeactors.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developersbreach.composeactors.core.database.dao.FavoriteActorsDao
import com.developersbreach.composeactors.core.database.dao.FavoriteMoviesDao
import com.developersbreach.composeactors.core.database.entity.FavoriteActorsEntity
import com.developersbreach.composeactors.core.database.entity.FavoriteMoviesEntity


@Database(
    entities = [FavoriteActorsEntity::class, FavoriteMoviesEntity::class],
    version = 4,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteActorsDao: FavoriteActorsDao
    abstract val favoriteMoviesDao: FavoriteMoviesDao
}