package com.developersbreach.composeactors.data.network

import com.developersbreach.composeactors.data.model.*
import org.json.JSONObject

/**
 * @property urls for low and high resolution images.
 *
 */
class JsonRemoteData(
    private val urls: RequestUrls
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
            val movieId = jsonObject.getInt("id")
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${urls.LOW_RES_IMAGE}$posterPathUrl"
            movieList.add(Movie(movieId, posterPath))
        }
        return movieList
    }

    @Throws(Exception::class)
    fun fetchMovieDetailsJsonData(
        response: String
    ): MovieDetail {

        val jsonObject = JSONObject(response)
        val movieId = jsonObject.getInt("id")
        val originalTitle = jsonObject.getString("original_title")
        val bannerUrl = jsonObject.getString("backdrop_path")
        val banner = "${urls.HIGH_RES_IMAGE}$bannerUrl"
        val budget = jsonObject.getString("budget")
        val originalLanguage = jsonObject.getString("original_language")
        val overview = jsonObject.getString("overview")
        val popularity = jsonObject.getDouble("popularity")
        val posterUrl = jsonObject.getString("poster_path")
        val poster = "${urls.HIGH_RES_IMAGE}$posterUrl"
        val releaseData = jsonObject.getString("release_date")
        val revenue = jsonObject.getLong("revenue")
        val runtime = jsonObject.getInt("runtime")
        val status = jsonObject.getString("status")
        val tagline = jsonObject.getString("tagline")
        val voteAverage = jsonObject.getDouble("vote_average")

        val genres: MutableList<Genre> = ArrayList()
        val genresArray = jsonObject.getJSONArray("genres")
        for (notI: Int in 0 until genresArray.length()) {
            val genresObject = genresArray.getJSONObject(notI)
            val genreId = genresObject.getInt("id")
            val genreName = genresObject.getString("name")
            genres.add(Genre(genreId, genreName))
        }

        var productionCompanies: List<String> = emptyList()
        val productionCompaniesArray = jsonObject.getJSONArray("production_companies")
        for (notI: Int in 0 until productionCompaniesArray.length()) {
            val productionCompaniesObject = productionCompaniesArray.getJSONObject(notI)
            val productionCompanyName = productionCompaniesObject.getString("name")
            productionCompanies = listOf(productionCompanyName)
        }

        return MovieDetail(
            movieId = movieId,
            movieTitle = originalTitle,
            banner = banner,
            budget = budget,
            genres = genres,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            poster = poster,
            productionCompanies = productionCompanies,
            releaseDate = releaseData,
            revenue = revenue,
            runtime = runtime,
            status = status,
            tagline = tagline,
            voteAverage = voteAverage
        )
    }

    /**
     * @return similar & recommended movies data list.
     */
    @Throws(Exception::class)
    fun fetchSimilarAndRecommendedMoviesJsonData(
        response: String
    ): List<Movie> {

        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val actorsJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until actorsJsonArray.length()) {
            val jsonObject = actorsJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${urls.LOW_RES_IMAGE}$posterPathUrl"
            movieList.add(Movie(movieId, posterPath))
        }
        return movieList
    }

    /**
     * @param response contains json response data to built a data upon.
     * @return list of [Actor] objects that has been built up from parsing a JSON response.
     */
    @Throws(Exception::class)
    fun fetchMovieCastByIdJsonData(
        response: String
    ): List<Cast> {

        val castList: MutableList<Cast> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val castJsonArray = baseJsonArray.getJSONArray("cast")

        for (notI: Int in 0 until castJsonArray.length()) {
            val jsonObject = castJsonArray.getJSONObject(notI)
            val actorId = jsonObject.getInt("id")
            val castName = jsonObject.getString("name")
            val profilePathUrl = jsonObject.getString("profile_path")
            val profilePath = "${urls.LOW_RES_IMAGE}$profilePathUrl"
            castList.add(Cast(actorId, castName, profilePath))
        }
        return castList
    }

    @Throws(Exception::class)
    fun fetchUpcomingMoviesJsonData(
        response: String
    ): List<Movie> {

        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val moviesJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until 5) {
            val jsonObject = moviesJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val backdropPathUrl = jsonObject.getString("backdrop_path")
            val backdropPath = "${urls.HIGH_RES_IMAGE}$backdropPathUrl"
            movieList.add(Movie(movieId, backdropPath))
        }
        return movieList
    }

    @Throws(Exception::class)
    fun fetchNowPlayingMoviesJsonData(
        response: String
    ): List<Movie> {

        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val moviesJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until moviesJsonArray.length()) {
            val jsonObject = moviesJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${urls.LOW_RES_IMAGE}$posterPathUrl"
            movieList.add(Movie(movieId, posterPath))
        }
        return movieList
    }
}