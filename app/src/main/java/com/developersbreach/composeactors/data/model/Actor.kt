package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class Actor(
    @Stable val actorId: Int,
    val actorName: String,
    val profileUrl: String
)