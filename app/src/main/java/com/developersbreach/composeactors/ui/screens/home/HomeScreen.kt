package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.components.UiStateHandler
import com.developersbreach.composeactors.ui.screens.search.SearchType

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSelectedPerson: (Int) -> Unit,
    navigateToSearch: (SearchType) -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToWatchlist: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    UiStateHandler(
        uiState = viewModel.uiState,
    ) { data ->
        HomeScreenUI(
            modifier = Modifier,
            navigateToWatchlist = navigateToWatchlist,
            navigateToSearch = navigateToSearch,
            navigateToAbout = navigateToAbout,
            navigateToProfile = navigateToProfile,
            navigateToSelectedPerson = navigateToSelectedPerson,
            navigateToSelectedMovie = navigateToSelectedMovie,
            data = data,
            updateHomeSearchType = { searchType: SearchType ->
                viewModel.updateHomeSearchType(searchType)
            },
        )
    }
}