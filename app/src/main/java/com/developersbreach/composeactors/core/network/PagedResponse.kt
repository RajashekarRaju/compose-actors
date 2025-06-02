package com.developersbreach.composeactors.core.network

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class PagedResponse<T> @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("total_pages")
    @JsonNames("totalPages")
    val totalPages: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val data: List<T>,
)