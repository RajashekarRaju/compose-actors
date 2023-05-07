package com.developersbreach.composeactors.ui.screens.actorDetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.screens.home.HomeScreen
import com.developersbreach.composeactors.ui.screens.search.SearchScreen


/**
 * Shows details of user selected actor.
 *
 * @param viewModel to manage ui state of [ActorDetailsScreen]
 * @param navigateUp navigates user to previous screen.
 *
 * This destination can be accessed from [HomeScreen] & [SearchScreen].
 */
@Composable
internal fun ActorDetailsScreen(
    viewModel: ActorDetailsViewModel = hiltViewModel(),
    navigateToSelectedMovie: (Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val detailUIState = viewModel.detailUIState
    val sheetUIState = viewModel.sheetUIState
    val movieId by viewModel.isFavoriteMovie.observeAsState()

    ActorDetailsUI(
        detailUIState = detailUIState,
        sheetUIState = sheetUIState,
        navigateToSelectedMovie = navigateToSelectedMovie,
        isFavoriteMovie = movieId != 0 && movieId != null,
        navigateUp = navigateUp,
        getSelectedMovieDetails = { viewModel.getSelectedMovieDetails(it) },
        addActorToFavorites = { viewModel.addActorToFavorites() },
        removeActorFromFavorites = { viewModel.removeActorFromFavorites() }
    )
}