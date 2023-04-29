package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
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
@Composable
fun HomeScreen(
    selectedActor: (Int) -> Unit,
    navigateToSearch: (SearchType) -> Unit,
    selectedMovie: (Int) -> Unit,
    navigateToFavorite: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val navigateToSearchBySearchType by homeViewModel.updateHomeSearchType.observeAsState(SearchType.Actors)

    HomeScreenUI(
        modifier = Modifier,
        navigateToFavorite = navigateToFavorite,
        navigateToSearch = navigateToSearch,
        navigateToSearchBySearchType = navigateToSearchBySearchType,
        selectedActor = selectedActor,
        selectedMovie = selectedMovie,
        uiState = homeViewModel.uiState,
        sheetUiState = homeViewModel.sheetUiState,
        updateHomeSearchType = { searchType: SearchType ->
            homeViewModel.updateHomeSearchType(searchType)
        }
    )
}