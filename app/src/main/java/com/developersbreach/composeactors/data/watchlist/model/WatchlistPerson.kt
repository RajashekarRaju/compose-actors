package com.developersbreach.composeactors.data.watchlist.model

import com.developersbreach.composeactors.core.network.LOW_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchlistPerson(
    @SerialName("personId") val personId: Int,
    @SerialName("personName") val personName: String,
    @SerialName("personProfileUrl") val personProfileUrl: String?,
    @SerialName("personPlaceOfBirth") val placeOfBirth: String? = null,
) {
    val profileUrl: String = "$LOW_RES_IMAGE$personProfileUrl"
}