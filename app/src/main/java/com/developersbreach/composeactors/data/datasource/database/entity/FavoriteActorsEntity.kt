package com.developersbreach.composeactors.data.datasource.database.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_actors_table")
data class FavoriteActorsEntity(

    @Stable
    @PrimaryKey
    @ColumnInfo(name = "column_actor_id")
    val actorId: Int,

    @ColumnInfo(name = "column_actor_name")
    val actorName: String,

    @ColumnInfo(name = "column_actor_profileUrl")
    val actorProfileUrl: String
)
