package com.developersbreach.composeactors.ui.screens.login

data class LoginData(
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val loginCompleted: Boolean? = null,
)