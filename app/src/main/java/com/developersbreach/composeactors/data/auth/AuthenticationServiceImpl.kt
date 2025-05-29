package com.developersbreach.composeactors.data.auth

import android.app.Activity
import arrow.core.Either
import com.amplifyframework.auth.AWSCognitoUserPoolTokens
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult
import com.amplifyframework.auth.options.AuthFetchSessionOptions
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.kotlin.core.Amplify
import com.developersbreach.composeactors.core.database.dao.SessionsDao
import com.developersbreach.composeactors.core.database.entity.SessionEntity
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class AuthenticationServiceImpl @Inject constructor(
    private val sessionsDao: SessionsDao,
) : AuthenticationService {

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
                true -> {
                    Timber.i("User SignedIn")
                    sessionsDao.addGuest(SessionEntity(isGuest = false))
                    Either.Right(Unit)
                }
                false -> {
                    Timber.e("User sign in exception ${result.nextStep}")
                    // TODO - Handle all cases and send error information to UI
                    Either.Left(Exception(result.nextStep.signInStep.name))
                }
            }
        } catch (e: Exception) {
            Timber.e("User sign in result exception $e")
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
                    Timber.e("HostedUI Error", it.exception)
                    Either.Left(it.exception)
                }
                signOut.globalSignOutError?.let {
                    Timber.e("GlobalSignOut Error", it.exception)
                    Either.Left(it.exception)
                }
                signOut.revokeTokenError?.let {
                    Timber.e("RevokeToken Error", it.exception)
                    Either.Left(it.exception)
                }
                Either.Right(Unit)
            }

            is AWSCognitoAuthSignOutResult.FailedSignOut -> {
                Timber.e("Sign out Failed", signOut.exception)
                Either.Left(signOut.exception)
            }

            else -> Either.Left(IllegalStateException("Unknown sign-out result: ${signOut::class.simpleName}"))
        }
    }

    override suspend fun isUserSignedIn(): Either<Throwable, Boolean> {
        return try {
            val isSignedIn = (Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession).isSignedIn
            Timber.i("isSignedIn: $isSignedIn")
            Either.Right(isSignedIn)
        } catch (e: AuthException) {
            Timber.e("Failed to fetch session", e)
            Either.Left(e)
        }
    }

    override suspend fun getTokens(): Either<Throwable, AWSCognitoUserPoolTokens> {
        return try {
            val tokens = (Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession).tokensResult.value
            if (tokens != null) {
                Either.Right(tokens)
            } else {
                Either.Left(
                    AuthException(
                        message = "Failed to fetch session",
                        recoverySuggestion = "SignIn user",
                    ),
                )
            }
        } catch (e: AuthException) {
            Timber.e("Failed to fetch session", e)
            Either.Left(e)
        }
    }

    override suspend fun refreshSession(): Either<Throwable, AWSCognitoUserPoolTokens> {
        return try {
            val options = AuthFetchSessionOptions.builder()
                .forceRefresh(true)
                .build()

            val session = Amplify.Auth.fetchAuthSession(options) as AWSCognitoAuthSession
            val tokensResult = session.userPoolTokensResult
            when (tokensResult.type) {
                AuthSessionResult.Type.SUCCESS -> {
                    val tokens = tokensResult.value
                    when {
                        tokens != null -> Either.Right(tokens)
                        else -> Either.Left(
                            AuthException(
                                "Cognito session returned no tokens",
                                "Expected access/refresh tokens but got null",
                            ),
                        )
                    }
                }

                AuthSessionResult.Type.FAILURE -> Either.Left(
                    tokensResult.error ?: AuthException(
                        message = "Unknown token error ${tokensResult.error}",
                        recoverySuggestion = tokensResult.error?.recoverySuggestion ?: "Unknown recovery suggestion",
                    ),
                )
            }
        } catch (e: Exception) {
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

    override suspend fun updateUserNameAttribute(
        name: String,
    ): Either<Throwable, Unit> {
        return updateUserAttributes(
            attribute = AuthUserAttribute(AuthUserAttributeKey.name(), name),
        )
    }

    private suspend fun updateUserAttributes(
        attribute: AuthUserAttribute,
    ): Either<Throwable, Unit> {
        return try {
            val result = Amplify.Auth.updateUserAttribute(attribute)
            when (result.isUpdated) {
                true -> Either.Right(Unit)
                false -> Either.Left(
                    AuthException(
                        message = "Failed to update user attributes $result",
                        recoverySuggestion = result.nextStep.toString(),
                    ),
                )
            }
        } catch (e: AuthException) {
            Either.Left(e)
        }
    }

    override suspend fun skipLogin(): Either<Throwable, Unit> {
        return try {
            Either.Right(sessionsDao.addGuest(SessionEntity(isGuest = true)))
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    override suspend fun isGuestUser(): Either<Throwable, Boolean> {
        return try {
            Either.Right(sessionsDao.isGuest())
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