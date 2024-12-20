package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteActorsEntity
import com.developersbreach.composeactors.utils.HIGH_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ActorDetail(
    @SerialName("id") @Stable val actorId: Int,
    @SerialName("name") val actorName: String,
    @SerialName("profile_path") private val profilePath: String?,
    @SerialName("biography") val biography: String,
    @SerialName("birthday") val dateOfBirth: String?,
    @SerialName("place_of_birth") val placeOfBirth: String?,
    @SerialName("popularity") val popularity: Double
) {
    val profileUrl: String = "$HIGH_RES_IMAGE$profilePath"
}

fun ActorDetail.toFavoriteActor() = FavoriteActor(
    actorId = this.actorId,
    actorName = this.actorName,
    profileUrl = this.profileUrl,
    placeOfBirth = this.placeOfBirth
)

fun FavoriteActor.actorAsDatabaseModel(): FavoriteActorsEntity {
    return FavoriteActorsEntity(
        actorId = this.actorId,
        actorName = this.actorName,
        actorProfileUrl = this.profileUrl,
        actorPlaceOfBirth = this.placeOfBirth
    )
}

fun List<FavoriteActorsEntity>.actorAsDomainModel(): List<FavoriteActor> {
    return map {
        FavoriteActor(
            actorId = it.actorId,
            actorName = it.actorName,
            profileUrl = it.actorProfileUrl,
            placeOfBirth = it.actorPlaceOfBirth
        )
    }
}