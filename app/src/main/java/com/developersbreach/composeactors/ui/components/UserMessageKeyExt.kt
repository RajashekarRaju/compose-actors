package com.developersbreach.composeactors.ui.components

import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.domain.core.UserMessageKey

internal fun UserMessageKey.asStringRes(): Int = when (this) {
    UserMessageKey.NetworkError -> R.string.network_error
    UserMessageKey.UnexpectedError -> R.string.unexpected_error
    UserMessageKey.InvalidEmailOrPassword -> R.string.error_invalid_email_or_password
    UserMessageKey.FailedLogin -> R.string.error_failed_login
    UserMessageKey.FieldsEmpty -> R.string.error_fields_empty
    UserMessageKey.PasswordMismatch -> R.string.error_password_mismatch
    UserMessageKey.VerificationRequired -> R.string.error_verification_required
    UserMessageKey.NoResultsFound -> R.string.message_no_results_found
    UserMessageKey.LoadMovieDetailsError -> R.string.error_load_movie_details
    UserMessageKey.LoadPersonDetailsError -> R.string.error_load_person_details
    UserMessageKey.WatchlistAdded -> R.string.watchlist_added
    UserMessageKey.WatchlistRemoved -> R.string.watchlist_removed
    UserMessageKey.MfaCodeRequired -> R.string.error_mfa_code_required
    UserMessageKey.NewPasswordRequired -> R.string.error_new_password_required
    UserMessageKey.ResetPasswordRequired -> R.string.error_reset_password_required
}
