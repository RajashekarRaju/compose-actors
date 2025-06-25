package com.developersbreach.composeactors.ui.components

import android.content.Context
import com.developersbreach.composeactors.R
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * Centralized error mapping utility for converting Throwables to AppError instances
 * and mapping errors to user-friendly messages.
 */
class ErrorMapper(private val context: Context) {

    /**
     * Converts a Throwable to an appropriate AppError instance
     */
    fun mapThrowableToAppError(throwable: Throwable): AppError {
        return when (throwable) {
            // Network related errors
            is UnknownHostException,
            is ConnectException -> AppError.NetworkError.NoConnection
            
            is SocketTimeoutException -> AppError.NetworkError.Timeout
            
            is SSLException -> AppError.NetworkError.ServerError
            
            is IOException -> {
                // Check if it's an HTTP error based on message
                val message = throwable.message?.lowercase()
                when {
                    message?.contains("401") == true -> AppError.AuthError.InvalidCredentials
                    message?.contains("403") == true -> AppError.AuthError.SessionExpired
                    message?.contains("404") == true -> AppError.NetworkError.HttpError(404, throwable.message, throwable)
                    message?.contains("500") == true -> AppError.NetworkError.ServerError
                    message?.contains("api key") == true -> AppError.NetworkError.ApiKeyMissing(throwable.message)
                    else -> AppError.NetworkError.ServerError
                }
            }
            
            // Authentication errors
            is SecurityException -> AppError.AuthError.InvalidCredentials
            
            // Validation errors
            is IllegalArgumentException -> {
                val message = throwable.message?.lowercase()
                when {
                    message?.contains("email") == true -> AppError.ValidationError.InvalidEmail(throwable.message)
                    message?.contains("password") == true -> AppError.ValidationError.InvalidPassword(throwable.message)
                    else -> AppError.ValidationError.InvalidInput("unknown", throwable.message)
                }
            }
            
            // Default to unknown error
            else -> AppError.UnknownError(throwable.message, throwable)
        }
    }

    /**
     * Maps an AppError to a user-friendly message using string resources
     */
    fun getErrorMessage(error: AppError): String {
        return when (error) {
            // Authentication errors
            is AppError.AuthError.InvalidCredentials -> 
                context.getString(R.string.error_invalid_credentials)
            is AppError.AuthError.SessionExpired -> 
                context.getString(R.string.error_session_expired)
            is AppError.AuthError.AccountLocked -> 
                context.getString(R.string.error_account_locked)
            is AppError.AuthError.LoginFailed -> 
                error.message ?: context.getString(R.string.error_login_failed)

            // Network errors
            is AppError.NetworkError.NoConnection -> 
                context.getString(R.string.error_no_connection)
            is AppError.NetworkError.Timeout -> 
                context.getString(R.string.error_timeout)
            is AppError.NetworkError.ServerError -> 
                context.getString(R.string.error_server_error)
            is AppError.NetworkError.HttpError -> 
                error.message ?: context.getString(R.string.error_http_error, error.code)
            is AppError.NetworkError.ApiKeyMissing -> 
                context.getString(R.string.missing_api_key_snackbar_message)

            // Validation errors
            is AppError.ValidationError.InvalidEmail -> 
                context.getString(R.string.error_invalid_email)
            is AppError.ValidationError.InvalidPassword -> 
                context.getString(R.string.error_invalid_password)
            is AppError.ValidationError.RequiredFieldEmpty -> 
                context.getString(R.string.error_required_field_empty, error.fieldName)
            is AppError.ValidationError.InvalidInput -> 
                error.message ?: context.getString(R.string.error_invalid_input, error.fieldName)

            // Unknown errors
            is AppError.UnknownError -> 
                error.message ?: context.getString(R.string.error_unknown)
        }
    }

    /**
     * Gets the title for an error based on its type
     */
    fun getErrorTitle(error: AppError): String {
        return when (error) {
            is AppError.AuthError -> context.getString(R.string.error_title_auth)
            is AppError.NetworkError -> context.getString(R.string.error_title_network)
            is AppError.ValidationError -> context.getString(R.string.error_title_validation)
            is AppError.UnknownError -> context.getString(R.string.error_title_unknown)
        }
    }

    /**
     * Determines if an error supports retry action
     */
    fun canRetry(error: AppError): Boolean {
        return when (error) {
            is AppError.NetworkError.NoConnection,
            is AppError.NetworkError.Timeout,
            is AppError.NetworkError.ServerError -> true
            is AppError.AuthError.SessionExpired -> true
            else -> false
        }
    }

    /**
     * Gets the retry action text for an error
     */
    fun getRetryActionText(error: AppError): String {
        return when (error) {
            is AppError.AuthError.SessionExpired -> context.getString(R.string.action_login_again)
            else -> context.getString(R.string.action_retry)
        }
    }
}

/**
 * Extension function to convert Throwable to AppError using ErrorMapper
 */
fun Throwable.toAppError(context: Context): AppError {
    return ErrorMapper(context).mapThrowableToAppError(this)
}