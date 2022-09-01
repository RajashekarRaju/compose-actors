package com.developersbreach.composeactors.utils

/**
 * Add your own TMDB_API key here.
 *
 * You can get your own Api Key from here [https://www.themoviedb.org/settings/api]
 */
object TmdbApiKey {
    const val TMDB_API_KEY = ""
}

fun isTmdbApiKeyNotValid(): Boolean {
    return TmdbApiKey.TMDB_API_KEY.isEmpty()
}
