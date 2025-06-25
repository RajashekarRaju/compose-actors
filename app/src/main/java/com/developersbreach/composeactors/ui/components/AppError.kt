package com.developersbreach.composeactors.ui.components

/**
 * Sealed class hierarchy for application errors.
 * Provides structured error types for better error handling and UI presentation.
 */
sealed class AppError(
    open val message: String? = null,
    open val cause: Throwable? = null
) {
    /**
     * Authentication related errors
     */
    sealed class AuthError(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : AppError(message, cause) {
        data object InvalidCredentials : AuthError()
        data object SessionExpired : AuthError()
        data object AccountLocked : AuthError()
        data class LoginFailed(
            override val message: String? = null,
            override val cause: Throwable? = null
        ) : AuthError(message, cause)
    }

    /**
     * Network related errors
     */
    sealed class NetworkError(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : AppError(message, cause) {
        data object NoConnection : NetworkError()
        data object Timeout : NetworkError()
        data object ServerError : NetworkError()
        data class HttpError(
            val code: Int,
            override val message: String? = null,
            override val cause: Throwable? = null
        ) : NetworkError(message, cause)
        data class ApiKeyMissing(
            override val message: String? = null
        ) : NetworkError(message)
    }

    /**
     * Validation related errors
     */
    sealed class ValidationError(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : AppError(message, cause) {
        data class InvalidEmail(
            override val message: String? = null
        ) : ValidationError(message)
        data class InvalidPassword(
            override val message: String? = null
        ) : ValidationError(message)
        data class RequiredFieldEmpty(
            val fieldName: String,
            override val message: String? = null
        ) : ValidationError(message)
        data class InvalidInput(
            val fieldName: String,
            override val message: String? = null
        ) : ValidationError(message)
    }

    /**
     * Unknown or unexpected errors
     */
    data class UnknownError(
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : AppError(message, cause)
}

/**
 * Error severity levels for determining appropriate UI presentation
 */
enum class ErrorSeverity {
    /**
     * Low severity - show as inline messages or subtle indicators
     * Examples: validation errors, form field errors
     */
    LOW,
    
    /**
     * Medium severity - show as snackbars or banners
     * Examples: network timeouts, recoverable API errors
     */
    MEDIUM,
    
    /**
     * High severity - show as dialogs for critical issues
     * Examples: authentication failures, unrecoverable errors
     */
    HIGH
}

/**
 * Extension function to determine error severity
 */
fun AppError.getSeverity(): ErrorSeverity = when (this) {
    is AppError.ValidationError -> ErrorSeverity.LOW
    is AppError.NetworkError.NoConnection,
    is AppError.NetworkError.Timeout,
    is AppError.NetworkError.ApiKeyMissing -> ErrorSeverity.MEDIUM
    is AppError.NetworkError.ServerError,
    is AppError.NetworkError.HttpError -> ErrorSeverity.MEDIUM
    is AppError.AuthError -> ErrorSeverity.HIGH
    is AppError.UnknownError -> ErrorSeverity.HIGH
}