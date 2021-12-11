package com.developersbreach.composeactors.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developersbreach.composeactors.ui.actors.Actors
import com.developersbreach.composeactors.ui.details.ActorDetails


@Composable
fun AppNavigation(
    startDestination: String = AppDestinations.ACTORS_ROUTE,
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
            AppDestinations.ACTORS_ROUTE
        ) {
            Actors(selectedActor = actions.selectedActor)
        }

        composable(
            route = "${AppDestinations.ACTOR_DETAIL_ROUTE}/{${routes.ACTOR_DETAIL_ID_KEY}}",
            arguments = listOf(
                navArgument(
                    routes.ACTOR_DETAIL_ID_KEY
                ) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            ActorDetails(
                actorId = arguments.getInt(routes.ACTOR_DETAIL_ID_KEY),
                navigateUp = actions.navigateUp
            )
        }
    }
}