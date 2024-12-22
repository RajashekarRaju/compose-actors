package com.developersbreach.composeactors.core.database.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_persons_table")
data class FavoritePersonsEntity(

    @Stable
    @PrimaryKey
    @ColumnInfo(name = "column_person_id")
    val personId: Int,

    @ColumnInfo(name = "column_person_name")
    val personName: String,

    @ColumnInfo(name = "column_person_profileUrl")
    val personProfileUrl: String,

    @ColumnInfo(name = "column_person_placeOfBirth")
    val personPlaceOfBirth: String?
)
