package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.material.rememberScaffoldState
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
    val scaffoldState = rememberScaffoldState()
    UiStateHandler(
        uiState = viewModel.uiState,
        scaffoldState = scaffoldState,
        uiEvent = viewModel.uiEvent,
        isLoading = viewModel.isLoading,
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
            scaffoldState = scaffoldState,
            updateHomeSearchType = { searchType: SearchType ->
                viewModel.updateHomeSearchType(searchType)
            },
        )
    }
}