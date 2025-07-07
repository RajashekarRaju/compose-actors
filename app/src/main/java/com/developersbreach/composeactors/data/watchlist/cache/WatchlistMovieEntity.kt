package com.developersbreach.composeactors.data.watchlist.cache

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistMovie

@Entity(tableName = "watchlist_movies_table")
data class WatchlistMovieEntity(
    @Stable
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "movie_name")
    val movieTitle: String,
    @ColumnInfo(name = "movie_posterUrl")
    val moviePosterUrl: String?,
    @ColumnInfo(name = "movie_banner")
    val movieBackdropUrl: String?,
)

fun WatchlistMovieEntity.toMovie(): Movie {
    return Movie(
        movieId = movieId,
        movieTitle = movieTitle,
        posterPathUrl = moviePosterUrl,
        backdropPathUrl = movieBackdropUrl,
    )
}

fun List<WatchlistMovie>.toWatchlistMoviesEntity(): List<WatchlistMovieEntity> {
    return map {
        WatchlistMovieEntity(
            movieId = it.movieId,
            movieTitle = it.movieTitle,
            moviePosterUrl = it.posterUrl,
            movieBackdropUrl = it.backdropUrl,
        )
    }
}