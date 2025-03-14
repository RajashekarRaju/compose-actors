package com.developersbreach.composeactors.data.movie.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.network.HIGH_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class MovieDetail(
    @SerialName("id") @Stable val movieId: Int,
    @SerialName("original_title") val movieTitle: String,
    @SerialName("backdrop_path") private val backdropPath: String?,
    @SerialName("budget") val budget: Int,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") private val posterPath: String?,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompanies>,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("revenue") val revenue: Long,
    @SerialName("runtime") val runtime: Int,
    @SerialName("status") val status: String,
    @SerialName("tagline") val tagline: String,
    @SerialName("vote_average") val voteAverage: Double,
) {
    val banner: String = "$HIGH_RES_IMAGE$backdropPath"
    val poster: String = "$HIGH_RES_IMAGE$posterPath"
}

@Serializable
data class ProductionCompanies(
    @SerialName("id") @Stable val id: Int,
    @SerialName("logo_path") private val logoPath: String?,
    @SerialName("name") val name: String?,
) {
    val banner: String = "$HIGH_RES_IMAGE$logoPath"
}