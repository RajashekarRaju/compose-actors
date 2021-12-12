package com.developersbreach.composeactors.repository

import android.net.Uri
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.utils.ApiKey
import com.developersbreach.composeactors.utils.NetworkQueryUtils
import org.json.JSONObject
import java.net.URL


/**
 * Builds Uri used to fetch data from the server.
 * @return The String to use to query the data from the server.
 */
object QueryBuilder {

    private const val SCHEME_AUTHORITY = "https://api.themoviedb.org"
    private const val APPEND_PATH_VERSION = "3"
    private const val APPEND_PATH_PERSON = "person"
    private const val APPEND_PATH_POPULAR = "popular"
    private const val QUERY_PARAMETER_API_KEY = "api_key"
    private const val QUERY_PARAMETER_API_KEY_VALUE = ApiKey.API_KEY

    /**
     * https://api.themoviedb.org/3/person/popular?api_key=
     */
    fun actorsBuilder(): String {
        val uriBuilder: Uri.Builder = baseUriBuilder()
        uriBuilder.appendPath(APPEND_PATH_PERSON)
        uriBuilder.appendPath(APPEND_PATH_POPULAR)
        uriBuilder.appendQueryParameter(QUERY_PARAMETER_API_KEY, QUERY_PARAMETER_API_KEY_VALUE)
        return uriBuilder.build().toString()
    }

    /**
     * https://developersbreach.com/wp-json/wp/v2
     */
    private fun baseUriBuilder(): Uri.Builder {
        val baseUri: Uri = Uri.parse(SCHEME_AUTHORITY)
        val uriBuilder: Uri.Builder = baseUri.buildUpon()
        uriBuilder.appendPath(APPEND_PATH_VERSION)
        return uriBuilder
    }
}

class JsonData {

    fun fetchActorsJsonData(
        response: String
    ): List<Actor> {

        val actorsList: MutableList<Actor> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val actorsJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until actorsJsonArray.length()) {
            val jsonObject = actorsJsonArray.getJSONObject(notI)
            val actorId = jsonObject.getInt("id")
            val actorName = jsonObject.getString("name")
            actorsList.add(Actor(actorId, actorName))
        }
        return actorsList
    }
}

class NetworkService {

    fun getActors(): List<Actor> {
        val response = actorsResponse()
        return JsonData().fetchActorsJsonData(response)
    }
}

/**
 * Returns new URL object from the given string URL.
 */
fun actorsResponse(): String {
    val uriString = QueryBuilder.actorsBuilder()
    val requestUrl = URL(uriString)
    val queryUtils = NetworkQueryUtils()
    return queryUtils.getResponseFromHttpUrl(requestUrl)
}
