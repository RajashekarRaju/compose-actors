package com.developersbreach.composeactors.data

data class PagedResponse<T>(
    val data: List<T>,
    val total: Int,
    val page: Int,
)