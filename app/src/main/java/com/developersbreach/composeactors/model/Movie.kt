package com.developersbreach.composeactors.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class Movie(
    @Stable val movieId: Int,
    val posterPathUrl: String
)