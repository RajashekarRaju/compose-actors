package com.developersbreach.composeactors.data.auth

import android.app.Activity
import arrow.core.Either

interface AuthenticationService {
    suspend fun signIn(
        email: String,
        password: String,
    ): Either<Throwable, Unit>

    suspend fun signInWithGoogle(
        activity: Activity,
    ): Either<Throwable, Unit>

    suspend fun signOut(): Either<Throwable, Unit>

    suspend fun getAccessToken(): Either<Throwable, String>

    suspend fun getCurrentUser(): Either<Throwable, AuthUserProfile>
}