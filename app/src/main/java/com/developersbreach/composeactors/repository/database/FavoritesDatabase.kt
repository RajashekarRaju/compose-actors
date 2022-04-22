package com.developersbreach.composeactors.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developersbreach.composeactors.repository.database.dao.FavoriteActorsDao
import com.developersbreach.composeactors.repository.database.dao.FavoriteMoviesDao
import com.developersbreach.composeactors.repository.database.entity.FavoriteActorsEntity
import com.developersbreach.composeactors.repository.database.entity.FavoriteMoviesEntity

private const val DATABASE_NAME = "favorites database"

@Database(
    entities = [FavoriteActorsEntity::class, FavoriteMoviesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract val favoriteActorsDao: FavoriteActorsDao
    abstract val favoriteMoviesDao: FavoriteMoviesDao
}

fun getDatabaseInstance(
    context: Context
): FavoritesDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        FavoritesDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}
