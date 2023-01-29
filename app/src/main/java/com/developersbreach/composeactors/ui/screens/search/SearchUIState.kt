package com.developersbreach.composeactors.ui.screens.search

import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.Movie

/**
 * UI state for the [SearchScreen] screen.
 */
sealed class SearchUIState {

    data class MovieSearch(
        val movieList: List<Movie> = listOf(),
        val isSearchingResults: Boolean = false,
    ): SearchUIState()

    data class ActorSearch(
        val actorList: List<Actor> = listOf(),
        val isSearchingResults: Boolean = false,
    ): SearchUIState()
}