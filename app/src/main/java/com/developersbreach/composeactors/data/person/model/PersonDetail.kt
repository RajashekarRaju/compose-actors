package com.developersbreach.composeactors.data.person.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity
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
    @SerialName("popularity") val popularity: Double,
) {
    val profileUrl: String = "$HIGH_RES_IMAGE$profilePath"
}

fun PersonDetail.toWatchlistPerson() = WatchlistPerson(
    personId = this.personId,
    personName = this.personName,
    profileUrl = this.profileUrl,
    placeOfBirth = this.placeOfBirth,
)

fun PersonDetail.toEntity(): PersonDetailEntity {
    return PersonDetailEntity(
        personId = personId,
        personName = personName,
        profilePath = profileUrl,
        biography = biography,
        dateOfBirth = dateOfBirth,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
    )
}