package com.developersbreach.composeactors.domain.core

sealed class AppError(
    override val message: String,
    override val cause: Throwable? = null,
) : ErrorMessage {
    override val severity: ErrorSeverity
        get() = when (this) {
            is NetworkError -> ErrorSeverity.NO_NETWORK
            is CriticalError -> ErrorSeverity.CRITICAL
        }

    data class NetworkError(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError(message, cause)

    data class CriticalError(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AppError(message, cause)
}