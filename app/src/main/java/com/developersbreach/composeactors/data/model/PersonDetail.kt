package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.database.entity.FavoritePersonsEntity
import com.developersbreach.composeactors.core.network.HIGH_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class PersonDetail(
    @SerialName("id") @Stable val personId: Int,
    @SerialName("name") val personName: String,
    @SerialName("profile_path") private val profilePath: String?,
    @SerialName("biography") val biography: String,
    @SerialName("birthday") val dateOfBirth: String?,
    @SerialName("place_of_birth") val placeOfBirth: String?,
    @SerialName("popularity") val popularity: Double
) {
    val profileUrl: String = "$HIGH_RES_IMAGE$profilePath"
}

fun PersonDetail.toFavoritePerson() = FavoritePerson(
    personId = this.personId,
    personName = this.personName,
    profileUrl = this.profileUrl,
    placeOfBirth = this.placeOfBirth
)

fun FavoritePerson.personAsDatabaseModel(): FavoritePersonsEntity {
    return FavoritePersonsEntity(
        personId = this.personId,
        personName = this.personName,
        personProfileUrl = this.profileUrl,
        personPlaceOfBirth = this.placeOfBirth
    )
}

fun List<FavoritePersonsEntity>.personAsDomainModel(): List<FavoritePerson> {
    return map {
        FavoritePerson(
            personId = it.personId,
            personName = it.personName,
            profileUrl = it.personProfileUrl,
            placeOfBirth = it.personPlaceOfBirth
        )
    }
}