package com.developersbreach.composeactors.domain.core

import kotlinx.io.IOException

interface ErrorReporter {
    fun reportError(error: ErrorMessage)

    fun setCustomKey(
        key: String,
        value: String,
    )
}

interface ErrorMessage {
    val severity: ErrorSeverity
    val message: String
    val cause: Throwable?
}

enum class ErrorSeverity {
    INFO, // User feedback only
    NO_NETWORK, // Connection issues
    CRITICAL, // Crashes, unexpected errors
}

fun Throwable.toAppError(): AppError = when (this) {
    is IOException -> AppError.NetworkError(localizedMessage.orEmpty(), this)
    else -> AppError.CriticalError(localizedMessage.orEmpty(), this)
}