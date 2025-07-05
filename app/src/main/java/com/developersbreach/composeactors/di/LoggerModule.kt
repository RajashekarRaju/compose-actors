package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.data.logging.CrashlyticsLogger
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()

    @Provides
    @Singleton
    fun provideErrorReporter(
        crashlytics: FirebaseCrashlytics,
    ): ErrorReporter = CrashlyticsLogger(crashlytics)
}