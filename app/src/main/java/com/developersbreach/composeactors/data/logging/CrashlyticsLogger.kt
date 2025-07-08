package com.developersbreach.composeactors.data.logging

import com.developersbreach.composeactors.domain.core.ErrorMessage
import com.developersbreach.composeactors.domain.core.ErrorReporter
import com.developersbreach.composeactors.domain.core.ErrorSeverity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrashlyticsLogger @Inject constructor(
    private val crashlytics: FirebaseCrashlytics,
) : ErrorReporter {
    override fun reportError(error: ErrorMessage) {
        if (error.severity == ErrorSeverity.CRITICAL) {
            crashlytics.apply {
                setCustomKey("error_type", error::class.java.simpleName)
                setCustomKey("error_message", error.message)
                error.cause?.let { recordException(it) }
            }
        }
    }

    override fun setCustomKey(
        key: String,
        value: String,
    ) {
        crashlytics.setCustomKey(key, value)
    }
}