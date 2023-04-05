package com.developersbreach.composeactors.data.model

data class MenuItem(private val title: String, private val iconId: Int) {

    fun getTitle(): String {
        return title
    }

    fun getIconId(): Int {
        return iconId
    }
}
