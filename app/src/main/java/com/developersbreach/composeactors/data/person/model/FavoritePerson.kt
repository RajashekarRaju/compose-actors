package com.developersbreach.composeactors.data.person.model

import androidx.compose.runtime.Stable

data class FavoritePerson(
    @Stable val personId: Int,
    val personName: String,
    val profileUrl: String,
    val placeOfBirth: String?
)