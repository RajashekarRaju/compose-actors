package com.developersbreach.composeactors.data.auth

data class AuthUserProfile(
    val username: String,
    val email: String?,
    val name: String?,
)