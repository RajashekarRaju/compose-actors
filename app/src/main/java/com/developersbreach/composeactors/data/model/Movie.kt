package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteMoviesEntity

@Immutable
data class Movie(
    @Stable val movieId: Int,
    val movieName: String,
    val posterPathUrl: String
)

fun Movie.movieAsDatabaseModel(): FavoriteMoviesEntity {
    return FavoriteMoviesEntity(
        movieId = this.movieId,
        movieName = this.movieName,
        moviePosterUrl = this.posterPathUrl,
    )
}

fun List<FavoriteMoviesEntity>.movieAsDomainModel(): List<Movie> {
    return map {
        Movie(
            movieId = it.movieId,
            movieName = it.movieName,
            posterPathUrl = it.moviePosterUrl
        )
    }
}