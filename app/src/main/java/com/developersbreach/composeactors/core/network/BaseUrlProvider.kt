package com.developersbreach.composeactors.core.network

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

open class BaseUrlProvider {

    fun String.toEncodedQuery(): String {
        return try {
            URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
        } catch (e: UnsupportedEncodingException) {
            throw IllegalArgumentException("Failed to encode query: $this", e)
        }
    }

    object TmdbConfig {
        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        const val TMDB_API_KEY = "api_key=${TmdbApiKey.TMDB_API_KEY}"
    }

    object ComposeActorsConfig {
        const val BASE_URL = "https://compose-actors-backend.onrender.com"
    }
}