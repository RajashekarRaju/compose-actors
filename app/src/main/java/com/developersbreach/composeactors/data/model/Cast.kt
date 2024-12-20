package com.developersbreach.composeactors.data.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.utils.LOW_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Cast(
    @SerialName("id") @Stable val actorId: Int,
    @SerialName("name") val castName: String,
    @SerialName("profile_path") private val profilePath: String?
) {
    val castProfilePath: String = "$LOW_RES_IMAGE$profilePath"
}

@Serializable
data class CastResponse(
    @SerialName("cast") val cast: List<Cast>,
)