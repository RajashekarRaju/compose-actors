package com.developersbreach.composeactors.data

import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import org.json.JSONObject

/**
 * @property urls for low and high resolution images.
 *
 */
class JsonRemoteData(
    private val urls: Urls
) {

    /**
     * @param response contains json response data to built a data upon.
     * @return list of [Actor] objects that has been built up from parsing a JSON response.
     */
    @Throws(Exception::class)
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
            val profilePathUrl = jsonObject.getString("profile_path")
            val profilePath = "${urls.LOW_RES_IMAGE}$profilePathUrl"
            actorsList.add(Actor(actorId, actorName, profilePath))
        }
        return actorsList
    }

    /**
     * @param response contains json response data to built a data upon.
     * @return list of [ActorDetail] objects that has been built up from parsing a JSON response.
     */
    @Throws(Exception::class)
    fun fetchActorDetailsJsonData(
        response: String
    ): ActorDetail {

        val jsonObject = JSONObject(response)
        val actorName = jsonObject.getString("name")
        val biography = jsonObject.getString("biography")
        val dateOfBirth = jsonObject.getString("birthday")
        val placeOfBirth = jsonObject.getString("place_of_birth")
        val popularity = jsonObject.getDouble("popularity")
        val profilePathUrl = jsonObject.getString("profile_path")
        val profilePath = "${urls.HIGH_RES_IMAGE}$profilePathUrl"

        return ActorDetail(actorName, profilePath, biography, dateOfBirth, placeOfBirth, popularity)
    }

    /**
     * @param response contains json response data to built a data upon.
     * @return [List] of [Movie] objects that has been built up from parsing a JSON response.
     */
    @Throws(Exception::class)
    fun fetchCastDetailsJsonData(
        response: String
    ): List<Movie> {
        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val movieJsonArray = baseJsonArray.getJSONArray("cast")

        for (notI: Int in 0 until movieJsonArray.length()) {
            val jsonObject = movieJsonArray.getJSONObject(notI)
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${urls.LOW_RES_IMAGE}$posterPathUrl"
            movieList.add(Movie(posterPath))
        }
        return movieList
    }
}