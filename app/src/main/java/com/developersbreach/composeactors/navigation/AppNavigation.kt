package com.developersbreach.composeactors.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developersbreach.composeactors.ui.actorDetails.ActorDetailScreen
import com.developersbreach.composeactors.ui.home.HomeScreen
import com.developersbreach.composeactors.ui.movieDetail.MovieDetailScreen
import com.developersbreach.composeactors.ui.search.SearchScreen


@Composable
fun AppNavigation(
    startDestination: String = AppDestinations.HOME_ROUTE,
    routes: AppDestinations = AppDestinations
) {
    val navController = rememberNavController()
    val actions = remember(navController) {
        AppActions(navController, routes)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            AppDestinations.HOME_ROUTE
        ) {
            HomeScreen(
                selectedActor = actions.selectedActor,
                navigateToSearch = actions.navigateToSearch,
                selectedMovie = actions.selectedMovie,
            )
        }

        composable(
            AppDestinations.SEARCH_ROUTE
        ) {
            SearchScreen(
                selectedActor = actions.selectedActor,
                navigateUp = actions.navigateUp
            )
        }

        composable(
            route = "${AppDestinations.ACTOR_DETAIL_ROUTE}/{${routes.ACTOR_DETAIL_ID_KEY}}",
            arguments = listOf(
                navArgument(routes.ACTOR_DETAIL_ID_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            ActorDetailScreen(
                selectedMovie = actions.selectedMovie,
                navigateUp = actions.navigateUp,
            )
        }

        composable(
            route = "${AppDestinations.MOVIE_DETAILS_ROUTE}/{${routes.MOVIE_DETAILS_ID_KEY}}",
            arguments = listOf(
                navArgument(
                    routes.MOVIE_DETAILS_ID_KEY
                ) {
                    type = NavType.IntType
                })
        ) {
            MovieDetailScreen(
                navigateUp = actions.navigateUp,
                selectedMovie = actions.selectedMovie,
            )
        }
    }
}