package com.developersbreach.composeactors.data.person.model

import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.database.entity.WatchlistPersonsEntity

data class WatchlistPerson(
    @Stable val personId: Int,
    val personName: String,
    val profileUrl: String,
    val placeOfBirth: String?,
)

fun WatchlistPerson.toWatchlistPersonsEntity(): WatchlistPersonsEntity {
    return WatchlistPersonsEntity(
        personId = this.personId,
        personName = this.personName,
        personProfileUrl = this.profileUrl,
        personPlaceOfBirth = this.placeOfBirth,
    )
}