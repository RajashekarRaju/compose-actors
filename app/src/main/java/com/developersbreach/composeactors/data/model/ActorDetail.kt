package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteActorsEntity

@Immutable
data class ActorDetail(
    @Stable val actorId: Int,
    val actorName: String,
    val profileUrl: String,
    val biography: String,
    val dateOfBirth: String,
    val placeOfBirth: String,
    val popularity: Double
)

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