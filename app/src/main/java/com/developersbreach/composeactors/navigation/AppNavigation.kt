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
import com.developersbreach.composeactors.ui.details.DetailScreen
import com.developersbreach.composeactors.ui.details.DetailsViewModel
import com.developersbreach.composeactors.ui.home.HomeScreen
import com.developersbreach.composeactors.ui.home.HomeViewModel
import com.developersbreach.composeactors.ui.movieDetail.MovieDetailScreen
import com.developersbreach.composeactors.ui.movieDetail.MovieDetailViewModel
import com.developersbreach.composeactors.ui.search.SearchScreen
import com.developersbreach.composeactors.ui.search.SearchViewModel


/**
 * @param startDestination default screen visible when user opens app.
 * @param routes gives access to all destination routes in [AppDestinations] object.
 *
 * This is an entry point triggered once activity starts.
 */
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

    // We need Application context and type cast to ComposeActorsApp so that we can access single
    // instance for the AppRepository class.
    val application = LocalContext.current.applicationContext as Application
    // Repository is then passed to all ViewModels with factories.
    val repository = (application as ComposeActorsApp).repository

    // Composables declared in NavHost can be controlled within by NavController.
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        /**
         * Start destination.
         * Can later navigate to [DetailScreen] and [SearchScreen]
         * Has it's own viewModel [HomeViewModel] with factory & repository instance.
         */
        composable(
            AppDestinations.HOME_ROUTE
        ) {
            HomeScreen(
                selectedActor = actions.selectedActor,
                navigateToSearch = actions.navigateToSearch,
                viewModel = viewModel(
                    factory = HomeViewModel.provideFactory(
                        application, repository
                    )
                )
            )
        }

        /**
         * Can later navigate to [DetailScreen]
         * Navigates back to previous screen with [AppActions.navigateUp]
         * Has it's own viewModel [SearchViewModel] with factory & repository instance.
         */
        composable(
            AppDestinations.SEARCH_ROUTE
        ) {
            SearchScreen(
                selectedActor = actions.selectedActor,
                navigateUp = actions.navigateUp,
                viewModel = viewModel(
                    factory = SearchViewModel.provideFactory(
                        application, repository
                    )
                )
            )
        }

        /**
         * Two destinations ([HomeScreen] [SearchScreen]) can navigate to this screen.
         * Navigates back to previous screen with [AppActions.navigateUp]
         * Has it's own viewModel [DetailsViewModel] with factory & repository instance.
         *
         * [AppDestinations.ACTOR_DETAIL_ID_KEY] contains id for the selected item in list.
         */
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
            DetailScreen(
                selectedMovie = actions.selectedMovie,
                navigateUp = actions.navigateUp,
                viewModel = viewModel(
                    factory = DetailsViewModel.provideFactory(
                        application, actorId, repository
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
                viewModel = viewModel(
                    factory = MovieDetailViewModel.provideFactory(
                        application, movieId, repository
                    )
                ),
                selectedMovie = actions.selectedMovie
            )
        }
    }
}