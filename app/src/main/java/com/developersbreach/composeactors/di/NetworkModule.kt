package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.repository.network.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkSource(): NetworkDataSource {
        return NetworkDataSource()
    }
}




