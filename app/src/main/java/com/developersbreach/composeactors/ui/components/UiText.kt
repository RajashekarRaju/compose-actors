package com.developersbreach.composeactors.ui.components

import android.content.Context
import com.developersbreach.composeactors.domain.core.UserMessageKey

sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    data class Message(val key: UserMessageKey, val args: List<Any> = emptyList()) : UiText()

    fun asString(context: Context): String = when (this) {
        is DynamicString -> value
        is Message -> context.getString(key.asStringRes(), *args.toTypedArray())
    }
}
