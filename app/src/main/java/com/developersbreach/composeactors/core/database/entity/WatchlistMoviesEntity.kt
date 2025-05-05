package com.developersbreach.composeactors.core.database.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.composeactors.data.movie.model.Movie

@Entity(tableName = "favorite_movies_table")
data class WatchlistMoviesEntity(
    @Stable
    @PrimaryKey
    @ColumnInfo(name = "column_movie_id")
    val movieId: Int,
    @ColumnInfo(name = "column_movie_name")
    val movieName: String,
    @ColumnInfo(name = "column_movie_posterUrl")
    val moviePosterUrl: String?,
    @ColumnInfo(name = "column_movie_banner")
    val movieBanner: String?,
)

fun List<WatchlistMoviesEntity>.movieAsDomainModel(): List<Movie> {
    return map {
        Movie(
            movieId = it.movieId,
            movieName = it.movieName,
            posterPath = it.moviePosterUrl,
            backdropPath = it.movieBanner,
        )
    }
}