package com.developersbreach.composeactors.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developersbreach.composeactors.ui.screens.about.AboutScreen
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.screens.favorites.FavoritesScreen
import com.developersbreach.composeactors.ui.screens.home.HomeScreen
import com.developersbreach.composeactors.ui.screens.movieDetail.MovieDetailScreen
import com.developersbreach.composeactors.ui.screens.search.SearchScreen

/**
 * This is an entry point triggered once activity starts.
 */
@Composable
fun AppNavigation(
    startDestination: AppDestinations = AppDestinations.Home,
) {
    val navController = rememberNavController()
    val actions = remember(navController) {
        AppActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<AppDestinations.Home> {
            HomeScreen(
                navigateToSelectedPerson = actions.navigateToSelectedPerson,
                navigateToSearch = actions.navigateToSearch,
                navigateToFavorite = actions.navigateToFavorite,
                navigateToSelectedMovie = actions.navigateToSelectedMovie,
                navigateToAbout = actions.navigateToAbout,
            )
        }

        composable<AppDestinations.Search> {
            SearchScreen(
                navigateUp = actions.navigateUp,
                navigateToSelectedPerson = actions.navigateToSelectedPerson,
                navigateToSelectedMovie = actions.navigateToSelectedMovie,
            )
        }

        composable<AppDestinations.ActorDetail> {
            ActorDetailsScreen(
                navigateToSelectedMovie = actions.navigateToSelectedMovie,
                navigateUp = actions.navigateUp,
            )
        }

        composable<AppDestinations.MovieDetail> {
            MovieDetailScreen(
                navigateUp = actions.navigateUp,
                navigateToSelectedMovie = actions.navigateToSelectedMovie,
            )
        }

        composable<AppDestinations.Favorites> {
            FavoritesScreen(
                navigateUp = actions.navigateUp,
                navigateToSelectedMovie = actions.navigateToSelectedMovie,
                navigateToSelectedPerson = actions.navigateToSelectedPerson,
            )
        }

        composable<AppDestinations.About> {
            AboutScreen(
                navigateUp = actions.navigateUp,
            )
        }
    }
}