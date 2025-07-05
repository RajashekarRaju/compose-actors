package com.developersbreach.composeactors.di

import android.content.Context
import androidx.room.Room
import com.developersbreach.composeactors.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val OLD_DATABASE_NAME = "favorites_database"
    private const val DATABASE_NAME = "compose_actors_database"

    @Provides
    @Singleton
    @JvmStatic
    fun getDatabaseInstance(
        @ApplicationContext context: Context,
    ): AppDatabase {
        renameDatabaseName(context)
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME,
        )
            .addMigrations(AppDatabase.MIGRATION_5_6, AppDatabase.MIGRATION_6_7, AppDatabase.MIGRATION_7_8)
            .build()
    }

    private fun renameDatabaseName(
        context: Context,
    ) {
        try {
            val oldFile = context.getDatabasePath(OLD_DATABASE_NAME)
            val newFile = context.getDatabasePath(DATABASE_NAME)
            if (oldFile.exists() && !newFile.exists()) {
                oldFile.parentFile?.mkdirs()
                oldFile.renameTo(newFile)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}