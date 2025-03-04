package com.developersbreach.composeactors.data.movie.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Genre(
    @SerialName("id") @Stable val genreId: Int,
    @SerialName("name") val genreName: String,
)