package com.developersbreach.composeactors.data.movie.model

import com.developersbreach.composeactors.core.network.HIGH_RES_IMAGE
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieProvidersResponse(
    @SerialName("results") val results: Map<String, CountryProviders>,
)

@Serializable
data class Flatrate(
    @SerialName("logo_path") private val logoPath: String,
    @SerialName("provider_id") val providerId: Int,
    @SerialName("provider_name") val providerName: String,
) {
    val logo: String = "$HIGH_RES_IMAGE$logoPath"
}

@Serializable
data class CountryProviders(
    @SerialName("flatrate") val flatrate: List<Flatrate> = emptyList(),
)