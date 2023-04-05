package com.developersbreach.composeactors.ui.screens.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.screens.home.tabs.FavoritesScreenUI
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(
    selectedMovie: (Int) -> Unit,
    favoriteViewModel: FavoriteViewModel) {

    val favoriteMovies by favoriteViewModel.favoriteMovies.observeAsState(emptyList())

    val scaffoldState = rememberScaffoldState()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = tween(durationMillis = 500),
        skipHalfExpanded = true
    )
    val openHomeBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState,
    )

    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
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
                    movie = favoriteViewModel.sheetUiState.selectedMovieDetails,
                    selectedMovie = selectedMovie)
            },
        ) {
            Scaffold(
                // attach snackbar host state to the scaffold
                scaffoldState = scaffoldState,
                topBar = { FavoritesTopBar() }
            ) { paddingValues ->
                Box(
                    modifier = Modifier.padding(paddingValues = paddingValues)
                ) {
                    // Main content for this screen
                    AnimatedVisibility(visibleState = state,
                        enter = fadeIn()
                    ) {
                        FavoritesScreenUI(
                            getSelectedMovieDetails = { movieId ->
                                favoriteViewModel.getSelectedMovieDetails(movieId)
                            },
                            openHomeBottomSheet = openHomeBottomSheet,
                            favoriteMovies = favoriteMovies
                        )
                    }
                }
            }
        }
    }
}

