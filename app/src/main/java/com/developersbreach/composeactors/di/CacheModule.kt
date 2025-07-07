package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.core.cache.CacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideCacheManager(): CacheManager {
        return CacheManager()
    }
}