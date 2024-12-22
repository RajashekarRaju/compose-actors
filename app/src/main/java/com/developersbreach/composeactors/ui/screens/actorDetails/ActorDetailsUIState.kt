package com.developersbreach.composeactors.ui.screens.actorDetails

import com.developersbreach.composeactors.data.model.PersonDetail
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.MovieDetail

// TODO - create a sealed class to contains the different states

/**
 * Models the UI state for the [ActorDetailsScreen] screen.
 */
data class ActorDetailsUIState(
    val castList: List<Movie> = listOf(),
    val actorData: PersonDetail? = null,
    val isFetchingDetails: Boolean = false,
)

/**
 * Models the UI state for the SheetContentMovieDetails modal sheet.
 */
data class ActorDetailsSheetUIState(
    val selectedMovieDetails: MovieDetail? = null
)