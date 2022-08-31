package com.developersbreach.composeactors.utils

/**
 * Add your own TMDB_API key here.
 *
 * You can get your own Api Key from here [https://www.themoviedb.org/settings/api]
 */
object TmdbApiKey {
    const val TMDB_API_KEY = "d77b5ab884174f60f4c9e8f50a70d99c"
}

fun isTmdbApiKeyNotValid(): Boolean {
    return TmdbApiKey.TMDB_API_KEY.isEmpty()
}