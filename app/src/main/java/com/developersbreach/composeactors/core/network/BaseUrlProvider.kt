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

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "api_key=${TmdbApiKey.TMDB_API_KEY}"
    }
}