package com.developersbreach.composeactors.navigation

import androidx.navigation.NavHostController

/**
 * @property navController helps us navigate by performing action.
 * @property routes destinations to navigate once action is triggered.
 */
class AppActions(
    private val navController: NavHostController,
    private val routes: AppDestinations
) {

    // Triggered when user tries to navigate to details of an actor from list with Id.
    val selectedActor: (Int) -> Unit = { actorId: Int ->
        navController.navigate("${routes.ACTOR_DETAIL_ROUTE}/$actorId")
    }

    // Triggered when user tries to navigate to details of an actor from list with Id.
    val selectedMovie: (Int) -> Unit = { movieId: Int ->
        navController.navigate("${routes.MOVIE_DETAILS_ROUTE}/$movieId")
    }

    // Navigates to SearchScreen
    val navigateToSearch = {
        navController.navigate(routes.SEARCH_ROUTE)
    }

    // Navigates to previous screen from current screen.
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}