package com.developersbreach.composeactors.ui.navigation

import androidx.navigation.NavHostController
import com.developersbreach.composeactors.ui.screens.search.SearchType

class NavigationActions(
    private val navController: NavHostController,
) {
    val navigateToHome: () -> Unit = {
        navController.navigate(AppDestinations.Home)
    }

    val navigateToSelectedPerson: (Int) -> Unit = { personId: Int ->
        navController.navigate(AppDestinations.ActorDetail(personId))
    }

    val navigateToSelectedMovie: (Int) -> Unit = { movieId: Int ->
        navController.navigate(AppDestinations.MovieDetail(movieId))
    }

    val navigateToSearch: (SearchType) -> Unit = { searchType: SearchType ->
        navController.navigate(AppDestinations.Search(searchType))
    }

    val navigateToFavorite: () -> Unit = {
        navController.navigate(AppDestinations.Favorites)
    }

    val navigateToAbout: () -> Unit = {
        navController.navigate(AppDestinations.About)
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}