package com.developersbreach.composeactors.ui.navigation

/**
 * All routes in one place.
 */
object AppDestinations {
    // Default destination
    const val HOME_ROUTE = "home"

    const val FAVORITES_ROUTE = "favorites"

    // destination for searching actors
    const val SEARCH_ROUTE = "search"
    // search based on type
    const val SEARCH_TYPE = "searchType"

    const val ABOUT_ROUTE = "about"

    // list to details destination
    const val ACTOR_DETAIL_ROUTE = "actor-detail"
    // list to selected actor detail destination
    const val ACTOR_DETAIL_ID_KEY = "personId"

    // destination for searching actors
    const val MOVIE_DETAILS_ROUTE = "movie-detail"
    // list to selected actor detail destination
    const val MOVIE_DETAILS_ID_KEY = "movieId"
}
