package com.developersbreach.composeactors.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.developersbreach.composeactors.ui.actors.ActorsScreen
import com.developersbreach.composeactors.ui.actors.ActorsViewModel
import com.developersbreach.composeactors.ui.details.DetailScreen
import com.developersbreach.composeactors.ui.details.DetailsViewModel
import com.developersbreach.composeactors.ui.search.SearchScreen
import com.developersbreach.composeactors.ui.search.SearchViewModel


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
            val actorsViewModel: ActorsViewModel = viewModel()
            ActorsScreen(
                selectedActor = actions.selectedActor,
                navigateToSearch = actions.navigateToSearch,
                viewModel = actorsViewModel,
            )
        }

        composable(
            AppDestinations.SEARCH_ROUTE
        ) {
            val searchViewModel: SearchViewModel = viewModel()
            SearchScreen(
                selectedActor = actions.selectedActor,
                viewModel = searchViewModel,
                navigateUp = actions.navigateUp,
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
            val actorId = arguments.getInt(routes.ACTOR_DETAIL_ID_KEY)
            val application = LocalContext.current.applicationContext

            val detailsViewModel: DetailsViewModel = viewModel(
                factory = DetailsViewModel.provideFactory(application as Application, actorId)
            )

            DetailScreen(
                navigateUp = actions.navigateUp,
                viewModel = detailsViewModel
            )
        }
    }
}