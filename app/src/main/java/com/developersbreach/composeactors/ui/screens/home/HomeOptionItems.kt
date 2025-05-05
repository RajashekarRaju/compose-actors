package com.developersbreach.composeactors.ui.screens.home

import com.developersbreach.composeactors.R

data class HomeOptionItems(
    val id: Int,
    val title: Int,
    val icon: Int,
) {
    companion object {
        val homeOptions = listOf(
            HomeOptionItems(
                id = 1,
                title = R.string.watchlist,
                icon = R.drawable.ic_added_to_watchlist,
            ),
            HomeOptionItems(
                id = 2,
                title = R.string.search,
                icon = R.drawable.ic_search,
            ),
            HomeOptionItems(
                id = 3,
                title = R.string.profile,
                icon = R.drawable.ic_account,
            ),
            HomeOptionItems(
                id = 4,
                title = R.string.about,
                icon = R.drawable.ic_about,
            ),
        )
    }
}