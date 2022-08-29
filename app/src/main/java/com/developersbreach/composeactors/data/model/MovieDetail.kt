package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class MovieDetail(
    @Stable val movieId: Int,
    val movieTitle: String,
    val banner: String,
    val budget: String,
    val genres: List<Genre>,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val poster: String,
    val productionCompanies: List<String>,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val voteAverage: Double
)
