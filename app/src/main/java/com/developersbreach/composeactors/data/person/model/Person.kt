package com.developersbreach.composeactors.data.person.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.developersbreach.composeactors.core.network.LOW_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Person(
    @SerialName("id") @Stable val personId: Int,
    @SerialName("name") val personName: String,
    @SerialName("profile_path") private val profilePath: String?
) {
    val profileUrl: String = "$LOW_RES_IMAGE$profilePath"
}