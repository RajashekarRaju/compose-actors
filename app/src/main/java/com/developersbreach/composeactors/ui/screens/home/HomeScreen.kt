package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.data.model.BottomSheetType
import com.developersbreach.composeactors.data.model.MenuItem
import com.developersbreach.composeactors.data.model.MovieDetail
import com.developersbreach.composeactors.ui.components.ApiKeyMissingShowSnackbar
import com.developersbreach.composeactors.ui.components.IfOfflineShowSnackbar
import com.developersbreach.composeactors.ui.screens.home.composables.HomeSnackbar
import com.developersbreach.composeactors.ui.screens.modalSheets.OptionsModalSheetContent
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
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
    navigateToFavorite: () -> Unit,
    selectedMovie: (Int) -> Unit,
    homeViewModel: HomeViewModel
) {
    // Remember state of scaffold to manage snackbar
    val scaffoldState = rememberScaffoldState()

    // Remember state of modal bottom sheet to manage bottom sheet
    var currentBottomSheet: BottomSheetType? by remember { mutableStateOf(BottomSheetType.MoreMenu) }

    val currentBottomSheetCallback: (BottomSheetType) -> Unit = { bottomSheetType ->
        currentBottomSheet = bottomSheetType
    }

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
                GetSheetContent(
                    currentBottomSheet,
                    homeViewModel.sheetUiState.selectedMovieDetails,
                    selectedMovie,
                    modalSheetState,
                    navigateToFavorite,
                    homeViewModel.getMenuItems()
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
                bottomBar = {
                    HomeBottomBar(
                        modalSheetSheet = modalSheetState,
                        currentBottomSheetCallback = currentBottomSheetCallback
                    )
                },
                // Host for custom snackbar
                snackbarHost = { HomeSnackbar(it) }
            ) { paddingValues ->
                Box(
                    modifier = Modifier.padding(paddingValues = paddingValues)
                ) {
                    // Main content for this screen
                    HomeScreenUI(
                        selectedActor = selectedActor,
                        currentBottomSheetCallback = currentBottomSheetCallback,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GetSheetContent(
    currentBottomSheet: BottomSheetType?,
    selectedMovieDetails: MovieDetail?,
    selectedMovie: (Int) -> Unit,
    modalSheetState: ModalBottomSheetState,
    navigateToFavorite: () -> Unit,
    menuItems: List<MenuItem>
) = when (currentBottomSheet) {
    BottomSheetType.MoreMenu -> {
        OptionsModalSheetContent(
            modalSheetState,
            navigateToFavorite = navigateToFavorite,
            selectedMovieDetail = selectedMovieDetails,
            menuItemList = menuItems
        )
    }
    BottomSheetType.MovieDetails -> {
        SheetContentMovieDetails(
            movie = selectedMovieDetails,
            selectedMovie = selectedMovie
        )
    }
    else -> {}
}

