package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.data.auth.AuthenticationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationServiceModule {

    @Binds
    @Singleton
    abstract fun bindAuthenticationService(
        impl: AuthenticationServiceImpl,
    ): AuthenticationService
}