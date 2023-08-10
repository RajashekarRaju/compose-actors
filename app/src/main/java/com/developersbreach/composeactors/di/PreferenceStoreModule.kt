package com.developersbreach.composeactors.di

import android.content.Context
import com.developersbreach.composeactors.data.datasource.database.PreferenceStoreDatabase
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceStoreModule {
    @Provides
    @Singleton
    fun providePreferenceStore(@ApplicationContext context: Context): PreferenceStoreDatabase {
        return PreferenceStoreDatabase(context = context.applicationContext)
    }
}