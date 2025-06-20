package com.developersbreach.composeactors.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.developersbreach.composeactors.core.database.dao.WatchlistPersonsDao
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesDao
import com.developersbreach.composeactors.core.database.dao.PersonDetailsDao
import com.developersbreach.composeactors.core.database.dao.SessionsDao
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesRemoteKeysDao
import com.developersbreach.composeactors.core.database.entity.WatchlistPersonsEntity
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity
import com.developersbreach.composeactors.core.database.entity.SessionEntity
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesRemoteKeysEntity
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesEntity

@Database(
    entities = [
        WatchlistPersonsEntity::class,
        WatchlistMoviesEntity::class,
        PersonDetailEntity::class,
        SessionEntity::class,
        WatchlistMoviesRemoteKeysEntity::class,
    ],
    version = 9,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val watchlistPersonsDao: WatchlistPersonsDao
    abstract val personDetailsDao: PersonDetailsDao
    abstract val watchlistMoviesDao: WatchlistMoviesDao
    abstract val sessionsDao: SessionsDao
    abstract val watchlistMoviesRemoteKeysDao: WatchlistMoviesRemoteKeysDao

    companion object {

        const val OLD_MOVIES_TABLE = "favorite_movies_table"
        const val NEW_MOVIES_TABLE = "watchlist_movies_table"

        const val OLD_PERSONS_TABLE = "favorite_persons_table"
        const val NEW_PERSONS_TABLE = "watchlist_persons_table"

        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(
                db: SupportSQLiteDatabase,
            ) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `watchlist_movie_remote_keys_table` (
                        `movieId` INTEGER NOT NULL PRIMARY KEY,
                        `prevKey`     INTEGER,
                        `nextKey`     INTEGER
                    )
                    """.trimIndent(),
                )
            }
        }

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(
                db: SupportSQLiteDatabase,
            ) {
                db.execSQL("ALTER TABLE `$OLD_MOVIES_TABLE` RENAME TO `$NEW_MOVIES_TABLE`")
                db.execSQL("ALTER TABLE `$OLD_PERSONS_TABLE` RENAME TO `$NEW_PERSONS_TABLE`")
            }
        }

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