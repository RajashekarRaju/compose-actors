package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie

@Composable
fun FavoritesScreen(
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToSelectedActor: (Int) -> Unit,
) {
    val favoriteMovies by favoriteViewModel.favoriteMovies.observeAsState(emptyList())
    val favoriteActors by favoriteViewModel.favoriteActors.observeAsState(emptyList())

    val removeFavoriteMovie = { movie: Movie ->
        favoriteViewModel.removeMovieFromFavorites(movie)
    }

    val removeFavoriteActor = { favoriteActor: FavoriteActor ->
        favoriteViewModel.removeActorFromFavorites(favoriteActor)
    }

    FavoritesScreenUI(
        navigateUp = navigateUp,
        favoriteMovies = favoriteMovies,
        navigateToSelectedMovie = navigateToSelectedMovie,
        removeFavoriteMovie = removeFavoriteMovie,
        navigateToSelectedActor = navigateToSelectedActor,
        favoriteActors = favoriteActors,
        removeFavoriteActor = removeFavoriteActor
    )
}