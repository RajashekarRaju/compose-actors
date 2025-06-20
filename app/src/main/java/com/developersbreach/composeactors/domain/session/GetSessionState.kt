package com.developersbreach.composeactors.domain.session

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.developersbreach.composeactors.data.auth.AuthenticationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSessionState @Inject constructor(
    private val authenticationService: AuthenticationService,
) {
    suspend operator fun invoke(): Either<Throwable, SessionState> {
        return when {
            authenticationService.isGuestUser() -> Either.Right(SessionState.Guest)
            else -> getSessionState()
        }
    }

    private suspend fun getSessionState(): Either<Throwable, SessionState> {
        return authenticationService.isUserSignedIn().fold(
            ifLeft = { it.left() },
            ifRight = { signedIn ->
                when {
                    signedIn -> SessionState.Authenticated
                    else -> SessionState.Unauthenticated
                }.right()
            },
        )
    }
}