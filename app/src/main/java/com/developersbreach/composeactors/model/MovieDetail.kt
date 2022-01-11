package com.developersbreach.composeactors.model

data class MovieDetail(
    val movieId: Int,
    val movieTitle: String,
    val banner: String,
    val budget: String,
    val genres: List<String>,
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    val poster: String,
    val productionCompanies: List<String>,
    val releaseData: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val voteAverage: Double
)
