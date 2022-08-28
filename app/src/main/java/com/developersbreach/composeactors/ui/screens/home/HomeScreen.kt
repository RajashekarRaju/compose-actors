package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.components.ApiKeyMissingShowSnackbar
import com.developersbreach.composeactors.ui.components.IfOfflineShowSnackbar
import com.developersbreach.composeactors.ui.screens.modalSheets.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.screens.modalSheets.manageModalBottomSheet
import com.developersbreach.composeactors.ui.screens.modalSheets.modalBottomSheetState

/**
 * @param selectedActor navigates to user clicked actor from row.
 * @param navigateToSearch navigates user to search screen.
 * @param viewModel to manage ui state of [HomeScreen]
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
    navigateToSearch: () -> Unit,
    selectedMovie: (Int) -> Unit,
    viewModel: HomeViewModel
) {
    // Remember state of scaffold to manage snackbar
    val scaffoldState = rememberScaffoldState()

    val modalSheetState = modalBottomSheetState()
    val openHomeBottomSheet = manageModalBottomSheet(
        modalSheetState = modalSheetState
    )

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
                    viewModel.sheetUiState.selectedMovieDetails,
                    selectedMovie
                )
            },
        ) {
            Scaffold(
                // attach snackbar host state to the scaffold
                scaffoldState = scaffoldState,
                // Custom AppBar contains fake search bar.
                topBar = { HomeTopAppBar(navigateToSearch) },
                // Host for custom snackbar
                snackbarHost = { hostState ->
                    SnackbarHost(hostState) { data ->
                        Snackbar(
                            snackbarData = data,
                            // To avoid colliding with navigation bar.
                            modifier = Modifier.padding(bottom = 48.dp)
                        )
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    // Main content for this screen
                    HomeScreenContent(selectedActor, viewModel, selectedMovie, openHomeBottomSheet)

                    // Perform network check and show snackbar if offline
                    IfOfflineShowSnackbar(scaffoldState)

                    // If Api key is missing, show a SnackBar.
                    ApiKeyMissingShowSnackbar(scaffoldState)
                }
            }
        }
    }
}