package com.developersbreach.composeactors.ui.navigation

import com.developersbreach.composeactors.ui.screens.search.SearchType
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestinations {

    @Serializable
    data object Splash : AppDestinations

    @Serializable
    data object Home : AppDestinations

    @Serializable
    data object Watchlist : AppDestinations

    @Serializable
    data class Search(
        val searchType: SearchType,
    ) : AppDestinations

    @Serializable
    data class ActorDetail(
        val personId: Int,
    ) : AppDestinations

    @Serializable
    data class MovieDetail(
        val movieId: Int,
    ) : AppDestinations

    @Serializable
    data object About : AppDestinations

    @Serializable
    data object Login : AppDestinations

    @Serializable
    data object Profile : AppDestinations
}