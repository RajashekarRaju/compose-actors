package com.developersbreach.composeactors.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.ui.screens.search.SearchType

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSelectedPerson: (Int) -> Unit,
    navigateToSearch: (SearchType) -> Unit,
    navigateToAbout: () -> Unit,
    navigateToSelectedMovie: (Int) -> Unit,
    navigateToFavorite: () -> Unit,
) {
    val navigateToSearchBySearchType by viewModel.updateHomeSearchType.observeAsState(SearchType.Persons)

    HomeScreenUI(
        modifier = Modifier,
        navigateToFavorite = navigateToFavorite,
        navigateToSearch = navigateToSearch,
        navigateToAbout = navigateToAbout,
        navigateToSearchBySearchType = navigateToSearchBySearchType,
        navigateToSelectedPerson = navigateToSelectedPerson,
        navigateToSelectedMovie = navigateToSelectedMovie,
        uiState = viewModel.uiState,
        sheetUiState = viewModel.sheetUiState,
        updateHomeSearchType = { searchType: SearchType ->
            viewModel.updateHomeSearchType(searchType)
        }
    )
}