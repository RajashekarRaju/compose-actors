package com.developersbreach.composeactors.ui.screens.search

import com.developersbreach.composeactors.data.model.Actor

// TODO - create a sealed class to contains the different states

/**
 * Models the UI state for the [SearchScreen] screen.
 */
data class SearchUIState(
    val actorList: ArrayList<Actor> = arrayListOf(),
    val isSearchingResults: Boolean = false,
)