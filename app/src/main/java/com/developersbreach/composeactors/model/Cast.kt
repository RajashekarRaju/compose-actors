package com.developersbreach.composeactors.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class Cast(
    @Stable val actorId: Int,
    val castName: String,
    val castProfilePath: String
)
