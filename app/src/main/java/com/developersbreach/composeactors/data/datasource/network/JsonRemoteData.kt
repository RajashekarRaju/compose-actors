package com.developersbreach.composeactors.data.datasource.network

import com.developersbreach.composeactors.data.PagedResponse
import com.developersbreach.composeactors.data.model.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import org.json.JSONObject

/**
 * @property urls for low and high resolution images.
 */
@Singleton
class JsonRemoteData @Inject constructor(
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

        if (response.isEmpty()) {
            return emptyList()
        }

        val actorsList: MutableList<Actor> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val actorsJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until actorsJsonArray.length()) {
            val jsonObject = actorsJsonArray.getJSONObject(notI)
            val actorId = jsonObject.getInt("id")
            val actorName = jsonObject.getString("name")
            val profilePathUrl = jsonObject.getString("profile_path")
            val profilePath = "${LOW_RES_IMAGE}$profilePathUrl"
            actorsList.add(Actor(actorId, actorName, profilePath))
        }
        return actorsList
    }

    @Throws(Exception::class)
    fun fetchMoviesJsonData(
        response: String
    ): List<Movie> {

        if (response.isEmpty()) {
            return emptyList()
        }

        val moviesList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val moviesJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until moviesJsonArray.length()) {
            val jsonObject = moviesJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val originalTitle = jsonObject.getString("original_title")
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${LOW_RES_IMAGE}$posterPathUrl"
            val bannerUrl = jsonObject.getString("backdrop_path")
            val banner = "${HIGH_RES_IMAGE}$bannerUrl"
            moviesList.add(Movie(movieId, originalTitle, posterPath, banner))
        }
        return moviesList
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
        val actorId = jsonObject.getInt("id")
        val actorName = jsonObject.getString("name")
        val biography = jsonObject.getString("biography")
        val dateOfBirth = jsonObject.getString("birthday")
        val placeOfBirth = jsonObject.getString("place_of_birth")
        val popularity = jsonObject.getDouble("popularity")
        val profilePathUrl = jsonObject.getString("profile_path")
        val profilePath = "${HIGH_RES_IMAGE}$profilePathUrl"

        return ActorDetail(
            actorId,
            actorName,
            profilePath,
            biography,
            dateOfBirth,
            placeOfBirth,
            popularity
        )
    }

    /**
     * @param response contains json response data to built a data upon.
     * @return [List] of [Movie] objects that has been built up from parsing a JSON response.
     */
    @Throws(Exception::class)
    fun fetchCastDetailsJsonData(
        response: String
    ): List<Movie> {

        if (response.isEmpty()) {
            return emptyList()
        }

        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val movieJsonArray = baseJsonArray.getJSONArray("cast")

        for (notI: Int in 0 until movieJsonArray.length()) {
            val jsonObject = movieJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val originalTitle = jsonObject.getString("original_title")
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${LOW_RES_IMAGE}$posterPathUrl"
            val bannerUrl = jsonObject.getString("backdrop_path")
            val banner = "${HIGH_RES_IMAGE}$bannerUrl"
            movieList.add(Movie(movieId, originalTitle, posterPath, banner))
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
        val banner = "${HIGH_RES_IMAGE}$bannerUrl"
        val budget = jsonObject.getString("budget")
        val originalLanguage = jsonObject.getString("original_language")
        val overview = jsonObject.getString("overview")
        val popularity = jsonObject.getDouble("popularity")
        val posterUrl = jsonObject.getString("poster_path")
        val poster = "${HIGH_RES_IMAGE}$posterUrl"
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

        if (response.isEmpty()) {
            return emptyList()
        }

        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val actorsJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until actorsJsonArray.length()) {
            val jsonObject = actorsJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val originalTitle = jsonObject.getString("original_title")
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${LOW_RES_IMAGE}$posterPathUrl"
            val bannerUrl = jsonObject.getString("backdrop_path")
            val banner = "${HIGH_RES_IMAGE}$bannerUrl"
            movieList.add(Movie(movieId, originalTitle, posterPath, banner))
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

        if (response.isEmpty()) {
            return emptyList()
        }

        val castList: MutableList<Cast> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val castJsonArray = baseJsonArray.getJSONArray("cast")

        for (notI: Int in 0 until castJsonArray.length()) {
            val jsonObject = castJsonArray.getJSONObject(notI)
            val actorId = jsonObject.getInt("id")
            val castName = jsonObject.getString("name")
            val profilePathUrl = jsonObject.getString("profile_path")
            val profilePath = "${LOW_RES_IMAGE}$profilePathUrl"
            castList.add(Cast(actorId, castName, profilePath))
        }
        return castList
    }

    @Throws(Exception::class)
    fun fetchUpcomingMoviesJsonData(
        response: String
    ): List<Movie> {

        if (response.isEmpty()) {
            return emptyList()
        }

        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val moviesJsonArray = baseJsonArray.getJSONArray("results")

        for (notI: Int in 0 until 5) {
            val jsonObject = moviesJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val originalTitle = jsonObject.getString("original_title")
            val backdropPathUrl = jsonObject.getString("backdrop_path")
            val backdropPath = "${HIGH_RES_IMAGE}$backdropPathUrl"
            val bannerUrl = jsonObject.getString("backdrop_path")
            val banner = "${HIGH_RES_IMAGE}$bannerUrl"
            movieList.add(Movie(movieId, originalTitle, backdropPath, banner))
        }
        return movieList
    }

    @Throws(Exception::class)
    fun fetchNowPlayingMoviesJsonData(
        response: String
    ): PagedResponse<Movie> {

        if (response.isEmpty()) {
            return PagedResponse(emptyList(), 0, 0)
        }

        val movieList: MutableList<Movie> = ArrayList()
        val baseJsonArray = JSONObject(response)
        val moviesJsonArray = baseJsonArray.getJSONArray("results")
        val totalResults = baseJsonArray.getInt("total_results")
        val page = baseJsonArray.getInt("page")

        for (notI: Int in 0 until moviesJsonArray.length()) {
            val jsonObject = moviesJsonArray.getJSONObject(notI)
            val movieId = jsonObject.getInt("id")
            val originalTitle = jsonObject.getString("original_title")
            val posterPathUrl = jsonObject.getString("poster_path")
            val posterPath = "${HIGH_RES_IMAGE}$posterPathUrl"
            val bannerUrl = jsonObject.getString("backdrop_path")
            val banner = "${HIGH_RES_IMAGE}$bannerUrl"
            movieList.add(Movie(movieId, originalTitle, posterPath, banner))
        }

        return PagedResponse(
            data = movieList,
            total = totalResults,
            page = page
        )
    }

    fun fetchMovieProvidersJsonData(response: String): MovieProvider {
        val movieProvider = MovieProvider(ArrayList())
        val baseJsonObj = JSONObject(response)
        val resultsJsonObj = baseJsonObj.getJSONObject("results")
        val countryCode = Locale.getDefault().country
        if (resultsJsonObj.has(countryCode)) {
            val inJsonObj = resultsJsonObj.getJSONObject(countryCode)
            if (inJsonObj.has("flatrate")) {
                val flatrateJsonArray = inJsonObj.getJSONArray("flatrate")
                for (notI: Int in 0 until flatrateJsonArray.length()) {
                    val jsonObject = flatrateJsonArray.getJSONObject(notI)
                    val providerId = jsonObject.getInt("provider_id")
                    val providerName = jsonObject.getString("provider_name")
                    val logoPathUrl = jsonObject.getString("logo_path")
                    val logoPath = "${LOW_RES_IMAGE}$logoPathUrl"
                    movieProvider.flatrate.add(Flatrate(logoPath, providerId, providerName))
                }
            }
            return movieProvider
        }
        return MovieProvider(ArrayList())
    }

    companion object {
        private const val LOW_RES_IMAGE = "https://image.tmdb.org/t/p/w200"
        private const val HIGH_RES_IMAGE = "https://image.tmdb.org/t/p/w500"
    }
}