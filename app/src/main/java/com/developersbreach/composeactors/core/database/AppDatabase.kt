package com.developersbreach.composeactors.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developersbreach.composeactors.core.database.dao.FavoriteMoviesDao
import com.developersbreach.composeactors.core.database.dao.FavoritePersonsDao
import com.developersbreach.composeactors.core.database.dao.PersonDetailsDao
import com.developersbreach.composeactors.core.database.entity.FavoriteMoviesEntity
import com.developersbreach.composeactors.core.database.entity.FavoritePersonsEntity
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity

@Database(
    entities = [FavoritePersonsEntity::class, FavoriteMoviesEntity::class, PersonDetailEntity::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoritePersonsDao: FavoritePersonsDao
    abstract val personDetailsDao: PersonDetailsDao
    abstract val favoriteMoviesDao: FavoriteMoviesDao
}