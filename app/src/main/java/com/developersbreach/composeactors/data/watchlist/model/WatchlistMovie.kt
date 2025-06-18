package com.developersbreach.composeactors.data.watchlist.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.network.HIGH_RES_IMAGE
import com.developersbreach.composeactors.core.network.LOW_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class WatchlistMovie(
    @SerialName("movieId") @Stable val movieId: Int,
    @SerialName("movieName") val movieName: String,
    @SerialName("moviePosterUrl") private val posterPath: String?,
    @SerialName("movieBannerUrl") private val backdropPath: String?,
) {
    val posterPathUrl: String = "$LOW_RES_IMAGE$posterPath"
    val bannerUrl: String = "$HIGH_RES_IMAGE$backdropPath"
}