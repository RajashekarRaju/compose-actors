package com.developersbreach.composeactors.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.developersbreach.composeactors.core.database.dao.WatchlistPersonsDao
import com.developersbreach.composeactors.core.database.dao.WatchlistMoviesDao
import com.developersbreach.composeactors.core.database.dao.PersonDetailsDao
import com.developersbreach.composeactors.core.database.dao.SessionsDao
import com.developersbreach.composeactors.core.database.entity.WatchlistPersonsEntity
import com.developersbreach.composeactors.core.database.entity.WatchlistMoviesEntity
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity
import com.developersbreach.composeactors.core.database.entity.SessionEntity

@Database(
    entities = [
        WatchlistPersonsEntity::class,
        WatchlistMoviesEntity::class,
        PersonDetailEntity::class,
        SessionEntity::class,
    ],
    version = 6,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val watchlistPersonsDao: WatchlistPersonsDao
    abstract val personDetailsDao: PersonDetailsDao
    abstract val watchlistMoviesDao: WatchlistMoviesDao
    abstract val sessionsDao: SessionsDao

    companion object {
        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(
                db: SupportSQLiteDatabase,
            ) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `session_table` (
                        `column_session_id` INTEGER PRIMARY KEY NOT NULL,
                        `column_is_guest`   INTEGER NOT NULL DEFAULT 0
                    )
                    """.trimIndent(),
                )
            }
        }
    }
}