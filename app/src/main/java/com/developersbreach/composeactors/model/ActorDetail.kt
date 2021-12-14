package com.developersbreach.composeactors.model

data class ActorDetail(
    val actorName: String,
    val profilePath: String,
    val biography: String,
    val dateOfBirth: String,
    val placeOfBirth: String,
    val popularity: Double
)
