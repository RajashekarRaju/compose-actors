package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.model.FavoritePerson

@Composable
fun FavoritesScreen(
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToSelectedPerson: (Int) -> Unit
) {
    val favoriteMovies by favoriteViewModel.favoriteMovies.observeAsState(emptyList())
    val favoritePersons by favoriteViewModel.favoritePersons.observeAsState(emptyList())

    val removeFavoriteMovie = { movie: Movie ->
        favoriteViewModel.removeMovieFromFavorites(movie)
    }

    val removeFavoritePerson = { favoritePerson: FavoritePerson ->
        favoriteViewModel.removePersonFromFavorites(favoritePerson)
    }

    FavoritesScreenUI(
        navigateUp = navigateUp,
        favoriteMovies = favoriteMovies,
        navigateToSelectedMovie = navigateToSelectedMovie,
        removeFavoriteMovie = removeFavoriteMovie,
        navigateToSelectedPerson = navigateToSelectedPerson,
        favoritePeople = favoritePersons,
        removeFavoritePerson = removeFavoritePerson
    )
}