package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.data.datasource.database.entity.FavoriteActorsEntity

@Immutable
data class Actor(
    @Stable val actorId: Int,
    val actorName: String,
    val profileUrl: String
)

fun Actor.actorAsDatabaseModel(): FavoriteActorsEntity {
    return FavoriteActorsEntity(
        actorId = this.actorId,
        actorName = this.actorName,
        actorProfileUrl = this.profileUrl
    )
}

fun List<FavoriteActorsEntity>.actorAsDomainModel(): List<Actor> {
    return map {
        Actor(
            actorId = it.actorId,
            actorName = it.actorName,
            profileUrl = it.actorProfileUrl
        )
    }
}