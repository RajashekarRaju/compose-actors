package com.developersbreach.composeactors.ui.screens.home

import android.widget.Toast
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersbreach.composeactors.R
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

    val setRegionSuccessesCallBack = remember { mutableStateOf(false) }
    val selectedCountryName = remember { mutableStateOf("") }

    if (setRegionSuccessesCallBack.value) {
        if (selectedCountryName.value.isNotEmpty()) {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.region_successfully_changed_to, selectedCountryName.value),
                Toast.LENGTH_LONG
            ).show()
        }
        setRegionSuccessesCallBack.value = false
    }

    val onRegionSelect: (String, String) -> Unit = { countryCode, countryName ->
        selectedCountryName.value = countryName
        viewModel.setRegion(
            countryCode = countryCode,
            setRegionSuccessesCallBack = setRegionSuccessesCallBack
        )
    }

    HomeScreenUI(
        modifier = Modifier,
        navigateToFavorite = navigateToFavorite,
        navigateToSearch = navigateToSearch,
        navigateToAbout = navigateToAbout,
        navigateToSearchBySearchType = navigateToSearchBySearchType,
        navigateToSelectedActor = navigateToSelectedActor,
        navigateToSelectedMovie = navigateToSelectedMovie,
        onRegionSelect = onRegionSelect,
        uiState = viewModel.uiState,
        sheetUiState = viewModel.sheetUiState,
        updateHomeSearchType = { searchType: SearchType ->
            viewModel.updateHomeSearchType(searchType)
        }
    )
}