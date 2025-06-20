package com.developersbreach.composeactors.data.movie.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.network.HIGH_RES_IMAGE
import com.developersbreach.composeactors.core.network.LOW_RES_IMAGE
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMoviesEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Movie(
    @SerialName("id") @Stable val movieId: Int,
    @SerialName("original_title") val movieName: String,
    @SerialName("poster_path") private val posterPath: String?,
    @SerialName("backdrop_path") private val backdropPath: String?,
) {
    val posterPathUrl: String = "$LOW_RES_IMAGE$posterPath"
    val bannerUrl: String = "$HIGH_RES_IMAGE$backdropPath"
}

@Serializable
data class MoviesResponse(
    @SerialName("cast") val movies: List<Movie>,
)

fun Movie.toWatchlistMoviesEntity(): WatchlistMoviesEntity {
    return WatchlistMoviesEntity(
        movieId = movieId,
        movieName = movieName,
        moviePosterUrl = posterPathUrl,
        movieBanner = bannerUrl,
    )
}