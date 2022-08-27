package com.developersbreach.composeactors.data.network

import com.developersbreach.composeactors.utils.TmdbApiKey
import java.net.URL

/**
 * Builds and returns URL used to fetch data from the server.
 */
object RequestUrls {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "api_key=${TmdbApiKey.TMDB_API_KEY}"
    const val LOW_RES_IMAGE = "https://image.tmdb.org/t/p/w200"
    const val HIGH_RES_IMAGE = "https://image.tmdb.org/t/p/w500"

    // https://api.themoviedb.org/3/person/popular?api_key=API_KEY
    fun getPopularActorsUrl(): URL {
        return URL("${BASE_URL}person/popular?$API_KEY")
    }

    // https://api.themoviedb.org/3/trending/person/week?api_key=API_KEY
    fun getTrendingActorsUrl(): URL {
        return URL("${BASE_URL}trending/person/week?$API_KEY")
    }

    // https://api.themoviedb.org/3/person/3233?api_key=API_KEY
    fun getActorDetailsUrl(
        actorId: Int
    ): URL {
        return URL("${BASE_URL}person/${actorId}?$API_KEY")
    }

    // https://api.themoviedb.org/3/person/3233/movie_credits?api_key=API_KEY
    fun getCastDetailsUrl(
        actorId: Int
    ): URL {
        return URL("${BASE_URL}person/${actorId}/movie_credits?$API_KEY")
    }

    // https://api.themoviedb.org/3/search/person?api_key=API_KEY&query=$pacino
    fun getSearchActorsUrl(
        query: String
    ): URL {
        return URL("${BASE_URL}search/person?$API_KEY&query=$query")
    }

    // https://api.themoviedb.org/3/movie/{movie_id}?api_key=API_KEY
    fun getMovieDetailsUrl(
        movieId: Int
    ): URL {
        return URL("${BASE_URL}movie/$movieId?$API_KEY")
    }

    // https://api.themoviedb.org/3/movie/{movie_id}/recommendations?api_key=API_KEY&page=1
    fun getRecommendedMoviesUrl(
        movieId: Int,
        page: Int = 1
    ): URL {
        return URL("${BASE_URL}movie/$movieId/recommendations?$API_KEY&page=$page")
    }

    // https://api.themoviedb.org/3/movie/{movie_id}/similar?api_key=API_KEY&page=1
    fun getSimilarMoviesUrl(
        movieId: Int,
        page: Int = 1
    ): URL {
        return URL("${BASE_URL}movie/$movieId/similar?$API_KEY&page=$page")
    }

    // 299536
    // https://api.themoviedb.org/3/movie/{movie_id}/credits?api_key=API_KEY
    fun getMovieCastUrl(
        movieId: Int
    ): URL {
        return URL("${BASE_URL}movie/$movieId/credits?$API_KEY")
    }

    // 299536
    // https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=API_KEY
    fun getMovieTrailerUrl(
        movieId: Int
    ): URL {
        return URL("${BASE_URL}movie/$movieId/videos?$API_KEY")
    }

    // https://img.youtube.com/vi/SUXWAEX2jlg/sddefault.jpg
    fun buildMovieTrailerThumbnail(
        trailerId: String
    ): URL {
        return URL("https://img.youtube.com/vi/$trailerId/sddefault.jpg")
    }

    // https://api.themoviedb.org/3/movie/upcoming?api_key=API_KEY&page=1
    fun getUpcomingMoviesUrl(
        page: Int = 1
    ): URL {
        return URL("${BASE_URL}movie/upcoming?$API_KEY&page=$page")
    }

    // https://api.themoviedb.org/3/movie/now_playing?api_key=API_KEY&page=1
    fun getNowPlayingMoviesUrl(
        page: Int = 1
    ): URL {
        return URL("${BASE_URL}movie/now_playing?$API_KEY&page=$page")
    }
}