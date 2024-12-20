package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Stable

data class FavoriteActor(
    @Stable val actorId: Int,
    val actorName: String,
    val profileUrl: String,
    val placeOfBirth: String?
)