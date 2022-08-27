package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class Genre(
    @Stable val genreId: Int,
    val genreName: String,
)
