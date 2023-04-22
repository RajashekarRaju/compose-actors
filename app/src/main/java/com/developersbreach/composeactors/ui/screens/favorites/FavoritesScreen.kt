package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(
    navigateUp: () -> Unit,
    selectedMovie: (Int) -> Unit,
    favoriteViewModel: FavoriteViewModel
) {
    val favoriteMovies by favoriteViewModel.favoriteMovies.observeAsState(emptyList())

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = tween(durationMillis = 500),
        skipHalfExpanded = true
    )

    val removeFavoriteMovie = { movie: Movie ->
        favoriteViewModel.removeMovieFromFavorites(movie)
    }

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = MaterialTheme.colors.background,
            sheetContent = {
                SheetContentMovieDetails(
                    movie = null,
                    selectedMovie = selectedMovie
                )
            }
        ) {
            Scaffold(
                topBar = {
                    FavoritesTopAppBar(
                        navigateUp = navigateUp
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier.padding(paddingValues = paddingValues)
                ) {
                    FavoritesScreenUI(
                        favoriteMovies = favoriteMovies,
                        selectedMovie = selectedMovie,
                        removeFavoriteMovie = removeFavoriteMovie,
                    )
                }
            }
        }
    }
}