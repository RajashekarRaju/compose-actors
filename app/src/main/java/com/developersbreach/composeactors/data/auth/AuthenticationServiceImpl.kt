package com.developersbreach.composeactors.data.auth

import android.app.Activity
import arrow.core.Either
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.kotlin.core.Amplify
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class AuthenticationServiceImpl @Inject constructor() : AuthenticationService {

    override suspend fun signIn(
        email: String,
        password: String,
    ): Either<Throwable, Unit> {
        return try {
            val result: AuthSignInResult = Amplify.Auth.signIn(
                username = email,
                password = password,
            )
            when (result.isSignedIn) {
                true -> Either.Right(Unit)
                false -> {
                    // TODO - Handle all cases and send error information to UI
                    Either.Left(Exception(result.nextStep.signInStep.name))
                }
            }
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    override suspend fun signInWithGoogle(
        activity: Activity,
    ): Either<Throwable, Unit> {
        val result: AuthSignInResult = Amplify.Auth.signInWithSocialWebUI(
            provider = AuthProvider.google(),
            callingActivity = activity,
        )
        return when (result.isSignedIn) {
            true -> Either.Right(Unit)
            false -> {
                // TODO - Handle all cases and send error information to UI
                Timber.e(result.nextStep.toString())
                Timber.e(result.nextStep.signInStep.name)
                Either.Left(Exception(result.nextStep.signInStep.name))
            }
        }
    }

    override suspend fun signOut(): Either<Throwable, Unit> {
        return when (val signOut = Amplify.Auth.signOut()) {
            is AWSCognitoAuthSignOutResult.CompleteSignOut -> Either.Right(Unit)

            is AWSCognitoAuthSignOutResult.PartialSignOut -> {
                signOut.hostedUIError?.let {
                    Timber.e("AuthenticationImpl", "HostedUI Error", it.exception)
                    Either.Left(it.exception)
                }
                signOut.globalSignOutError?.let {
                    Timber.e("AuthenticationImpl", "GlobalSignOut Error", it.exception)
                    Either.Left(it.exception)
                }
                signOut.revokeTokenError?.let {
                    Timber.e("AuthenticationImpl", "RevokeToken Error", it.exception)
                    Either.Left(it.exception)
                }
                Either.Right(Unit)
            }

            is AWSCognitoAuthSignOutResult.FailedSignOut -> {
                Timber.e("AuthenticationImpl", "Sign out Failed", signOut.exception)
                Either.Left(signOut.exception)
            }

            else -> Either.Left(IllegalStateException("Unknown sign-out result: ${signOut::class.simpleName}"))
        }
    }

    override suspend fun getAccessToken(): Either<Throwable, String> {
        return try {
            val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession
            val accessToken = session.accessToken
            Timber.i("AuthenticationImpl", "accessToken: $accessToken")
            if (accessToken != null) {
                Either.Right(accessToken)
            } else {
                Either.Left(
                    AuthException(
                        message = "Failed to fetch session",
                        recoverySuggestion = "SignIn user",
                    ),
                )
            }
        } catch (e: AuthException) {
            Timber.e("AuthenticationImpl", "Failed to fetch session", e)
            Either.Left(e)
        }
    }

    override suspend fun getCurrentUser(): Either<Throwable, AuthUserProfile> {
        return try {
            Either.Right(
                AuthUserProfile(
                    username = Amplify.Auth.getCurrentUser().username,
                    email = find("email"),
                    name = find("name"),
                ),
            )
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    private suspend fun find(key: String): String? {
        return Amplify.Auth.fetchUserAttributes().firstOrNull {
            it.key.keyString == key
        }?.value
    }
}