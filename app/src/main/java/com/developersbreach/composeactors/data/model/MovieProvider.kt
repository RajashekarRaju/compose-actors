package com.developersbreach.composeactors.data.model

data class MovieProvider(
    val flatrate: ArrayList<Flatrate>,
)

data class Flatrate(
    val logo_path: String,
    val provider_id: Int,
    val provider_name: String
)