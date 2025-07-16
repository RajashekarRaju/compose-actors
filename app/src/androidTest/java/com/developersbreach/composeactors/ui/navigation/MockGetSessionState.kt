package com.developersbreach.composeactors.ui.navigation

import arrow.core.Either
import arrow.core.right
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.domain.session.GetSessionState
import com.developersbreach.composeactors.domain.session.SessionState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay

internal fun mockGetSessionState(
    authenticationService: AuthenticationService,
): GetSessionState = object : GetSessionState(authenticationService) {
    override suspend fun invoke(): Either<Throwable, SessionState> {
        delay(3_000)
        return SessionState.Unauthenticated.right()
    }
}

internal fun mockAuthService(): AuthenticationService {
    return mockk<AuthenticationService>(relaxed = true).apply {
        coEvery { isGuestUser() } returns false
        coEvery { isUserSignedIn() } returns Either.Right(false)
    }
}