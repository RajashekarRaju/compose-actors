package com.developersbreach.composeactors.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<T>(
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("page") val page: Int,
    @SerialName("results") val data: List<T>,
)