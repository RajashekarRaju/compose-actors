package com.developersbreach.composeactors.data.watchlist.cache

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson

@Entity(tableName = "watchlist_people_table")
data class WatchlistPersonEntity(
    @Stable
    @PrimaryKey
    @ColumnInfo(name = "person_id")
    val personId: Int,
    @ColumnInfo(name = "person_name")
    val personName: String,
    @ColumnInfo(name = "person_profileUrl")
    val personProfileUrl: String,
    @ColumnInfo(name = "person_placeOfBirth")
    val personPlaceOfBirth: String?,
)

fun WatchlistPersonEntity.toWatchlistPerson(): WatchlistPerson {
    return WatchlistPerson(
        personId = personId,
        personName = personName,
        personProfileUrl = personProfileUrl,
        placeOfBirth = personPlaceOfBirth,
    )
}

fun List<WatchlistPerson>.toWatchlistPeopleEntity(): List<WatchlistPersonEntity> {
    return map {
        WatchlistPersonEntity(
            personId = it.personId,
            personName = it.personName,
            personProfileUrl = it.profileUrl,
            personPlaceOfBirth = it.placeOfBirth,
        )
    }
}