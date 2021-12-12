package com.developersbreach.composeactors.utils

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class NetworkQueryUtils {

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    fun getResponseFromHttpUrl(
        url: URL
    ): String {
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        return try {
            val stream: InputStream = urlConnection.inputStream
            val scanner = Scanner(stream)
            scanner.useDelimiter("\\A")
            val hasInput: Boolean = scanner.hasNext()
            var response = ""
            if (hasInput) {
                response = scanner.next()
            }
            scanner.close()
            response
        } finally {
            urlConnection.disconnect()
        }
    }
}