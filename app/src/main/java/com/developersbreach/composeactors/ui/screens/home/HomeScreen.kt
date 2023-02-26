package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.ApiKeyMissingShowSnackbar
import com.developersbreach.composeactors.ui.components.IfOfflineShowSnackbar
import com.developersbreach.composeactors.ui.screens.home.composables.HomeSnackbar
import com.developersbreach.composeactors.ui.screens.modalSheets.OptionsModalSheetContent
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState
import com.developersbreach.composeactors.ui.screens.search.SearchType

/**
 * @param selectedActor navigates to user clicked actor from row.
 * @param navigateToSearch navigates user to search screen.
 * @param homeViewModel to manage ui state of [HomeScreen]
 *
 * Default destination.
 * Shows category list of actors in row.
 * Shows [HomeTopAppBar] search box looking ui in [TopAppBar]
 * If user is offline shows snackbar message.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    selectedActor: (Int) -> Unit,
    navigateToSearch: (SearchType) -> Unit,
    selectedMovie: (Int) -> Unit,
    homeViewModel: HomeViewModel
) {
    // Remember state of scaffold to manage snackbar
    val scaffoldState = rememberScaffoldState()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = tween(durationMillis = 500),
        skipHalfExpanded = true
    )
    val openHomeBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState
    )

    val favoriteMovies by homeViewModel.favoriteMovies.observeAsState(emptyList())
    val navigateToSearchBySearchType by homeViewModel.updateHomeSearchType.observeAsState(SearchType.Actors)

    Surface(
        color = MaterialTheme.colors.background,
    ) {
        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            scrimColor = Color.Black.copy(alpha = 0.5f),
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = MaterialTheme.colors.background,
            sheetContent = {
                OptionsModalSheetContent(
                  modalSheetState
                )
            },
        ) {
            Scaffold(
                // attach snackbar host state to the scaffold
                scaffoldState = scaffoldState,
                // Custom AppBar contains fake search bar.
                topBar = {
                    HomeTopAppBar(
                        navigateToSearch = navigateToSearch,
                        searchType = navigateToSearchBySearchType
                    )
                },
                bottomBar = { HomeBottomBar(modalSheetSheet = modalSheetState) },
                // Host for custom snackbar
                snackbarHost = { HomeSnackbar(it) }
            ) { paddingValues ->
                Box(
                    modifier = Modifier.padding(paddingValues = paddingValues)
                ) {
                    // Main content for this screen
                    HomeScreenUI(
                        selectedActor = selectedActor,
                        openHomeBottomSheet = openHomeBottomSheet,
                        homeUIState = homeViewModel.uiState,
                        homeSheetUIState = homeViewModel.sheetUiState,
                        favoriteMovies = favoriteMovies,
                        selectedMovie = { movieId ->
                            homeViewModel.getSelectedMovieDetails(movieId)
                        },
                        updateSearchType = { searchType: SearchType ->
                            homeViewModel.updateHomeSearchType(searchType)
                        }
                    )

                    // Perform network check and show snackbar if offline
                    IfOfflineShowSnackbar(scaffoldState)

                    // If Api key is missing, show a SnackBar.
                    ApiKeyMissingShowSnackbar(scaffoldState)
                }
            }
        }
    }
}