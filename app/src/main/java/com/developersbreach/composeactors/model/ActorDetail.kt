package com.developersbreach.composeactors.model

import androidx.compose.runtime.Immutable

@Immutable
data class ActorDetail(
    val actorName: String,
    val profileUrl: String,
    val biography: String,
    val dateOfBirth: String,
    val placeOfBirth: String,
    val popularity: Double
)
