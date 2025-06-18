package com.developersbreach.composeactors.data.auth

import android.app.Activity
import arrow.core.Either
import com.amplifyframework.auth.AWSCognitoUserPoolTokens

interface AuthenticationService {
    suspend fun signIn(
        email: String,
        password: String,
    ): Either<Throwable, Unit>

    suspend fun signInWithGoogle(
        activity: Activity,
    ): Either<Throwable, Unit>

    suspend fun signOut(): Either<Throwable, Unit>

    suspend fun isUserSignedIn(): Either<Throwable, Boolean>

    suspend fun getTokens(): Either<Throwable, AWSCognitoUserPoolTokens>

    suspend fun refreshSession(): Either<Throwable, AWSCognitoUserPoolTokens>

    suspend fun getCurrentUser(): Either<Throwable, AuthUserProfile>

    suspend fun updateUserNameAttribute(
        name: String,
    ): Either<Throwable, Unit>

    suspend fun skipLogin(): Either<Throwable, Unit>

    suspend fun isGuestUser(): Boolean
}