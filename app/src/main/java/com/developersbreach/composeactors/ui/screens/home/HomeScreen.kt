package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.screens.search.SearchType

/**
 * @param navigateToSelectedActor navigates to user clicked actor from row.
 * @param navigateToSearch navigates user to search screen.
 * @param viewModel to manage ui state of [HomeScreen]
 *
 * Default destination.
 * Shows category list of actors in row.
 * Shows [HomeTopAppBar] search box looking ui in [TopAppBar]
 * If user is offline shows snackbar message.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSelectedActor: (Int) -> Unit,
    navigateToSearch: (SearchType) -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToFavorite: () -> Unit,
) {
    val navigateToSearchBySearchType by viewModel.updateHomeSearchType.observeAsState(SearchType.Actors)

    HomeScreenUI(
        modifier = Modifier,
        navigateToFavorite = navigateToFavorite,
        navigateToSearch = navigateToSearch,
        navigateToAbout = navigateToAbout,
        navigateToSearchBySearchType = navigateToSearchBySearchType,
        navigateToSelectedActor = navigateToSelectedActor,
        navigateToSelectedMovie = navigateToSelectedMovie,
        uiState = viewModel.uiState,
        sheetUiState = viewModel.sheetUiState,
        updateHomeSearchType = { searchType: SearchType ->
            viewModel.updateHomeSearchType(searchType)
        }
    )
}