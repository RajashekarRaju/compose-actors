package com.developersbreach.composeactors.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developersbreach.composeactors.data.datasource.database.dao.FavoriteActorsDao
import com.developersbreach.composeactors.data.datasource.database.dao.FavoriteMoviesDao
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteActorsEntity
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteMoviesEntity

private const val DATABASE_NAME = "favorites database"

@Database(
    entities = [FavoriteActorsEntity::class, FavoriteMoviesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteActorsDao: FavoriteActorsDao
    abstract val favoriteMoviesDao: FavoriteMoviesDao
}

fun getDatabaseInstance(
    context: Context
): AppDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}
