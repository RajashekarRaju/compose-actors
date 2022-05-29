package com.developersbreach.composeactors.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.developersbreach.composeactors.ui.modalSheet.SheetContentMovieDetails
import com.developersbreach.composeactors.ui.components.ApiKeyMissingShowSnackbar
import com.developersbreach.composeactors.ui.components.IfOfflineShowSnackbar
import com.developersbreach.composeactors.ui.components.TransparentRippleTheme

/**
 * @param selectedActor navigates to user clicked actor from row.
 * @param navigateToSearch navigates user to search screen.
 * @param viewModel to manage ui state of [HomeScreen]
 *
 * Default destination.
 * Shows category list of actors in row.
 * Shows [HomeAppBar] search box looking ui in [TopAppBar]
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

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
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
                topBar = { MainAppBar(navigateToSearch) },
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
                    ScreenContent(selectedActor, viewModel, selectedMovie, modalSheetState)

                    // Perform network check and show snackbar if offline
                    IfOfflineShowSnackbar(scaffoldState)

                    // If Api key is missing, show a SnackBar.
                    ApiKeyMissingShowSnackbar(scaffoldState)
                }
            }
        }
    }
}

// Main content for this screen.
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    selectedActor: (Int) -> Unit,
    viewModel: HomeViewModel,
    selectedMovie: (Int) -> Unit,
    modalSheetState: ModalBottomSheetState
) {
    val tabPage = rememberSaveable { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize()
    ) {

        TabsContainer(tabPage)

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (tabPage.value) {
                0 -> ActorsTabContent(viewModel, selectedActor)
                1 -> MoviesTabContent(viewModel, selectedMovie, modalSheetState)
                2 -> FavoritesTabContent(viewModel, selectedMovie, modalSheetState)
            }
        }

        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}

@Composable
private fun TabsContainer(
    tabPage: MutableState<Int>
) {
    CompositionLocalProvider(
        LocalRippleTheme provides TransparentRippleTheme
    ) {
        TabRow(
            backgroundColor = MaterialTheme.colors.background,
            selectedTabIndex = tabPage.value,
            indicator = { tabPositions ->
                RoundedTabIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabPage.value])
                )
            }
        ) {
            tabs.forEachIndexed { tabIndex, currentTab ->
                Tab(
                    selected = tabPage.value == tabIndex,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.primary.copy(0.50f),
                    onClick = { tabPage.value = tabIndex },
                    text = {
                        Text(
                            text = currentTab.tabName,
                            style = MaterialTheme.typography.subtitle2
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun RoundedTabIndicator(
    modifier: Modifier
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(2.dp)
            .background(
                MaterialTheme.colors.primary,
                RoundedCornerShape(percent = 100)
            )
    )
}

@Immutable
data class Tabs(
    val tabName: String
)

private val tabs = listOf(
    Tabs("Actors"),
    Tabs("Movies"),
    Tabs("Favorites")
)