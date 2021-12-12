package com.developersbreach.composeactors.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developersbreach.composeactors.ui.actors.ActorsScreen
import com.developersbreach.composeactors.ui.actors.ActorsViewModel
import com.developersbreach.composeactors.ui.details.DetailScreen
import com.developersbreach.composeactors.ui.search.SearchScreen


@Composable
fun AppNavigation(
    startDestination: String = AppDestinations.ACTORS_ROUTE,
    routes: AppDestinations = AppDestinations,
    actorsViewModel: ActorsViewModel = viewModel()
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
            ActorsScreen(
                selectedActor = actions.selectedActor,
                viewModel = actorsViewModel
            )
        }

        composable(
            AppDestinations.SEARCH_ROUTE
        ) {
            SearchScreen(
                selectedActor = actions.selectedActor,
                viewModel = actorsViewModel
            )
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
            DetailScreen(
                actorId = arguments.getInt(routes.ACTOR_DETAIL_ID_KEY),
                navigateUp = actions.navigateUp,
                viewModel = actorsViewModel
            )
        }
    }
}