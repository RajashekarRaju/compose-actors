package com.developersbreach.composeactors.data.watchlist.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist_person_remote_keys_table")
data class WatchlistPersonRemoteKeysEntity(
    @PrimaryKey
    @ColumnInfo("person_id")
    val personId: Int,
    @ColumnInfo("prev_key")
    val prevKey: Int?,
    @ColumnInfo("next_key")
    val nextKey: Int?,
)