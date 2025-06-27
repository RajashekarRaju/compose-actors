package com.developersbreach.composeactors.ui.components

import com.developersbreach.composeactors.R

typealias UiMessage = Any

sealed class UiEvent {
    data class ShowMessage(
        val message: UiMessage,
    ) : UiEvent()
}

data class UiMessageWithResId(
    val resId: Int,
    val formatArgs: List<String>? = null,
)

interface ErrorMessage {
    fun toUiMessageWithResId(): UiMessageWithResId
}

sealed class CommonUiMessage : ErrorMessage {
    data class NetworkError(val message: String? = null) : CommonUiMessage()

    data class DatabaseError(val message: String) : CommonUiMessage()

    data class AuthenticationError(val message: String) : CommonUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is NetworkError -> UiMessageWithResId(R.string.network_error)
        is DatabaseError -> UiMessageWithResId(R.string.database_error, listOf(message))
        is AuthenticationError -> UiMessageWithResId(R.string.authentication_error, listOf(message))
    }
}