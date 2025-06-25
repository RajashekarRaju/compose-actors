package com.developersbreach.composeactors.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developersbreach.composeactors.ui.screens.about.AboutScreen
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsScreen
import com.developersbreach.composeactors.ui.screens.watchlist.WatchlistScreen
import com.developersbreach.composeactors.ui.screens.home.HomeScreen
import com.developersbreach.composeactors.ui.screens.login.LoginScreen
import com.developersbreach.composeactors.ui.screens.movieDetail.MovieDetailScreen
import com.developersbreach.composeactors.ui.screens.profile.ProfileScreen
import com.developersbreach.composeactors.ui.screens.search.SearchScreen
import com.developersbreach.composeactors.ui.screens.splash.SplashScreen
import com.developersbreach.composeactors.ui.screens.signup.SignUpScreen

/**
 * This is an entry point triggered once activity starts.
 */
@Composable
fun AppNavigation(
    startDestination: AppDestinations = AppDestinations.Splash,
) {
    val navController = rememberNavController()
    val actions = remember(navController) {
        NavigationActions(navController)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<AppDestinations.Splash> {
            SplashScreen(
                navigateToHome = { actions.navigateToHome() },
                navigateToLogin = { actions.navigateToLogin() },
            )
        }

        composable<AppDestinations.Home> {
            HomeScreen(
                navigateToSelectedPerson = actions.navigateToSelectedPerson,
                navigateToSearch = actions.navigateToSearch,
                navigateToWatchlist = actions.navigateToWatchlist,
                navigateToSelectedMovie = actions.navigateToSelectedMovie,
                navigateToAbout = actions.navigateToAbout,
                navigateToProfile = actions.navigateToProfile,
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

        composable<AppDestinations.Watchlist> {
            WatchlistScreen(
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

        composable<AppDestinations.Login> {
            LoginScreen(
                navigateToHome = {
                    navController.popBackStack()
                    actions.navigateToHome()
                },
                navigateToSignUp = actions.navigateToSignUp,
            )
        }

        composable<AppDestinations.Profile> {
            ProfileScreen(
                navigateUp = { actions.navigateUp() },
                navigateToLogin = {
                    navController.popBackStack()
                    actions.navigateToLogin()
                },
            )
        }

        composable<AppDestinations.SignUp> {
            SignUpScreen(
                navigateUp = { actions.navigateUp() },
                navigateToHome = {
                    navController.popBackStack()
                    actions.navigateToHome()
                },
            )
        }
    }
}