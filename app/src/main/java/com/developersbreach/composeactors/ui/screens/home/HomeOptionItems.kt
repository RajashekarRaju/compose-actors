package com.developersbreach.composeactors.ui.screens.home

import com.developersbreach.composeactors.R

data class HomeOptionItems(
    val id: Int,
    val title: String,
    val icon: Int
) {
    companion object {
        val homeOptions = listOf(
            HomeOptionItems(
                id = 1,
                title = "Favorites",
                icon = R.drawable.ic_favorite
            ),
            HomeOptionItems(
                id = 2,
                title = "Search",
                icon = R.drawable.ic_search
            ),
            HomeOptionItems(
                id = 3,
                title = "Profile",
                icon = R.drawable.ic_account
            ),
            HomeOptionItems(
                id = 4,
                title = "About",
                icon = R.drawable.ic_about
            )
        )
    }
}