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
import com.developersbreach.composeactors.ComposeActorsApp
import com.developersbreach.composeactors.ui.actorDetails.ActorDetailScreen
import com.developersbreach.composeactors.ui.home.HomeScreen
import com.developersbreach.composeactors.ui.movieDetail.MovieDetailScreen
import com.developersbreach.composeactors.ui.search.SearchScreen
import com.developersbreach.composeactors.utils.viewModelFactory


@Composable
fun AppNavigation(
    startDestination: String = AppDestinations.HOME_ROUTE,
    routes: AppDestinations = AppDestinations
) {
    // Create a NavHostController to handle navigation.
    val navController = rememberNavController()
    val actions = remember(navController) {
        AppActions(navController, routes)
    }

    val application = LocalContext.current.applicationContext as Application
    val repository = (application as ComposeActorsApp).repository
    val networkRepository = repository.getNetworkRepository
    val databaseRepository = repository.getDatabaseRepository

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
                viewModel = viewModel(
                    factory = viewModelFactory(
                        application, networkRepository, databaseRepository
                    )
                )
            )
        }

        composable(
            AppDestinations.SEARCH_ROUTE
        ) {
            SearchScreen(
                selectedActor = actions.selectedActor,
                navigateUp = actions.navigateUp,
                viewModel = viewModel(
                    factory = viewModelFactory(
                        application, networkRepository, databaseRepository
                    )
                )
            )
        }

        composable(
            route = "${AppDestinations.ACTOR_DETAIL_ROUTE}/{${routes.ACTOR_DETAIL_ID_KEY}}",
            arguments = listOf(
                navArgument(
                    routes.ACTOR_DETAIL_ID_KEY
                ) {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val actorId = arguments.getInt(routes.ACTOR_DETAIL_ID_KEY)
            ActorDetailScreen(
                selectedMovie = actions.selectedMovie,
                navigateUp = actions.navigateUp,
                viewModel = viewModel(
                    factory = viewModelFactory(
                        application, networkRepository, databaseRepository, actorId
                    )
                )
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
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val movieId = arguments.getInt(routes.MOVIE_DETAILS_ID_KEY)
            MovieDetailScreen(
                navigateUp = actions.navigateUp,
                selectedMovie = actions.selectedMovie,
                viewModel = viewModel(
                    factory = viewModelFactory(
                        application, networkRepository, databaseRepository, movieId
                    )
                )
            )
        }
    }
}