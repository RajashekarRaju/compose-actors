package com.developersbreach.composeactors.ui.screens.actorDetails.data.model

import com.developersbreach.composeactors.data.model.MovieDetail

/**
 * Models the UI state for the SheetContentMovieDetails modal sheet.
 */
data class SheetUIState(
    val selectedMovieDetails: MovieDetail? = null
)