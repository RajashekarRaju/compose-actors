package com.developersbreach.composeactors.data.watchlist.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist_movie_remote_keys_table")
data class WatchlistMoviesRemoteKeysEntity(
    @PrimaryKey
    @ColumnInfo("movie_id")
    val movieId: Int,
    @ColumnInfo("prev_key")
    val prevKey: Int?,
    @ColumnInfo("next_key")
    val nextKey: Int?,
)