package com.developersbreach.composeactors.data.watchlist.cache

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistMovie

@Entity(tableName = "watchlist_movies_table")
data class WatchlistMoviesEntity(
    @Stable
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
    @ColumnInfo(name = "movie_name")
    val movieName: String,
    @ColumnInfo(name = "movie_posterUrl")
    val moviePosterUrl: String?,
    @ColumnInfo(name = "movie_banner")
    val movieBanner: String?,
)

fun WatchlistMoviesEntity.toMovie(): Movie {
    return Movie(
        movieId = movieId,
        movieName = movieName,
        posterPath = moviePosterUrl,
        backdropPath = movieBanner,
    )
}

fun List<WatchlistMovie>.toWatchlistMoviesEntity(): List<WatchlistMoviesEntity> {
    return map {
        WatchlistMoviesEntity(
            movieId = it.movieId,
            movieName = it.movieName,
            moviePosterUrl = it.posterPathUrl,
            movieBanner = it.bannerUrl,
        )
    }
}