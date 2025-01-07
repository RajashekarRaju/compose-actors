package com.developersbreach.composeactors.data.person.model

import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.database.entity.FavoritePersonsEntity

data class FavoritePerson(
    @Stable val personId: Int,
    val personName: String,
    val profileUrl: String,
    val placeOfBirth: String?
)

fun FavoritePerson.FavoritePersonsEntity(): FavoritePersonsEntity {
    return FavoritePersonsEntity(
        personId = this.personId,
        personName = this.personName,
        personProfileUrl = this.profileUrl,
        personPlaceOfBirth = this.placeOfBirth
    )
}