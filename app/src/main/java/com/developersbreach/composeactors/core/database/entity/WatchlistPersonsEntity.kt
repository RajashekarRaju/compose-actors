package com.developersbreach.composeactors.core.database.entity

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.composeactors.data.person.model.WatchlistPerson

@Entity(tableName = "watchlist_persons_table")
data class WatchlistPersonsEntity(
    @Stable
    @PrimaryKey
    @ColumnInfo(name = "column_person_id")
    val personId: Int,
    @ColumnInfo(name = "column_person_name")
    val personName: String,
    @ColumnInfo(name = "column_person_profileUrl")
    val personProfileUrl: String,
    @ColumnInfo(name = "column_person_placeOfBirth")
    val personPlaceOfBirth: String?,
)

fun List<WatchlistPersonsEntity>.toWatchlistPersons(): List<WatchlistPerson> {
    return map {
        WatchlistPerson(
            personId = it.personId,
            personName = it.personName,
            profileUrl = it.personProfileUrl,
            placeOfBirth = it.personPlaceOfBirth,
        )
    }
}