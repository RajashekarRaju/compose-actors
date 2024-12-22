package com.developersbreach.composeactors.core.database.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies_table")
data class FavoriteMoviesEntity(

    @Stable
    @PrimaryKey
    @ColumnInfo(name = "column_movie_id")
    val movieId: Int,

    @ColumnInfo(name = "column_movie_name")
    val movieName: String,

    @ColumnInfo(name = "column_movie_posterUrl")
    val moviePosterUrl: String?,

    @ColumnInfo(name = "column_movie_banner")
    val movieBanner: String?
)
