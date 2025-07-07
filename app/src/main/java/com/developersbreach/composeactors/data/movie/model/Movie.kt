package com.developersbreach.composeactors.data.movie.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.network.HIGH_RES_IMAGE
import com.developersbreach.composeactors.core.network.LOW_RES_IMAGE
import com.developersbreach.composeactors.data.watchlist.cache.WatchlistMovieEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Movie(
    @SerialName("id") @Stable val movieId: Int,
    @SerialName("original_title") val movieTitle: String,
    @SerialName("poster_path") private val posterPathUrl: String?,
    @SerialName("backdrop_path") private val backdropPathUrl: String?,
) {
    val posterUrl: String = "$LOW_RES_IMAGE$posterPathUrl"
    val backdropUrl: String = "$HIGH_RES_IMAGE$backdropPathUrl"
}

@Serializable
data class MoviesResponse(
    @SerialName("cast") val movies: List<Movie>,
)

fun Movie.toWatchlistMovieEntity(): WatchlistMovieEntity {
    return WatchlistMovieEntity(
        movieId = movieId,
        movieTitle = movieTitle,
        moviePosterUrl = posterUrl,
        movieBackdropUrl = backdropUrl,
    )
}