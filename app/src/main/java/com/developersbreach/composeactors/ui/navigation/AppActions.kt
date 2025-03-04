package com.developersbreach.composeactors.ui.navigation

import androidx.navigation.NavHostController
import com.developersbreach.composeactors.ui.screens.search.SearchType

/**
 * @property navController helps us navigate by performing action.
 * @property routes destinations to navigate once action is triggered.
 */
class AppActions(
    private val navController: NavHostController,
    private val routes: AppDestinations,
) {

    // Triggered when user tries to navigate to details of an person from list with Id.
    val navigateToSelectedPerson: (Int) -> Unit = { personId: Int ->
        navController.navigate("${routes.ACTOR_DETAIL_ROUTE}/$personId")
    }

    // Triggered when user tries to navigate to details of an person from list with Id.
    val navigateToSelectedMovie: (Int) -> Unit = { movieId: Int ->
        navController.navigate("${routes.MOVIE_DETAILS_ROUTE}/$movieId")
    }

    // Navigates to SearchScreen
    val navigateToSearch: (SearchType) -> Unit = { searchType: SearchType ->
        navController.navigate("${routes.SEARCH_ROUTE}/$searchType")
    }

    // Triggered when user tries to navigate to favorite screen from More menu.
    val navigateToFavorite: () -> Unit = {
        navController.navigate(routes.FAVORITES_ROUTE)
    }

    // Navigates to about screen from home options menu
    val navigateToAbout: () -> Unit = {
        navController.navigate(routes.ABOUT_ROUTE)
    }

    // Navigates to previous screen from current screen.
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}