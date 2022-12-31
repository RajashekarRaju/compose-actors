package com.developersbreach.composeactors.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.developersbreach.composeactors.ui.components.ShowSearchProgress
import com.developersbreach.composeactors.ui.screens.home.HomeScreen


/**
 * @param selectedActor navigates to user clicked actor from row.
 * @param viewModel to manage ui state of [SearchScreen]
 * @param navigateUp navigates user to previous screen.
 *
 * This destination can be accessed only from [HomeScreen].
 * Shows searchable category list of actors in row.
 * Shows [SearchAppBar] search box looking ui in [TopAppBar]
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    selectedActor: (Int) -> Unit,
    viewModel: SearchViewModel,
    navigateUp: () -> Unit
) {
    val uiState = viewModel.uiState
    val keyboardController = LocalSoftwareKeyboardController.current
    val closeKeyboard = {
        keyboardController?.hide()
    }

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                SearchAppBar(navigateUp, viewModel, closeKeyboard)
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                // Show progress while search is happening
                val isLoadingData = !uiState.isSearchingResults && uiState.actorList.isEmpty()
                ShowSearchProgress(isLoadingData)
                // Main content for this screen
                SearchUI(uiState.actorList, selectedActor, closeKeyboard)
            }
        }
    }
}