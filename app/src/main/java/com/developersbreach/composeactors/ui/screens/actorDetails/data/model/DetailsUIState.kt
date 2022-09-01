package com.developersbreach.composeactors.ui.screens.actorDetails.data.model

import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.Movie

/**
 * Models the UI state for the [ActorDetailScreen] screen.
 */
data class DetailsUIState(
    val castList: List<Movie> = listOf(),
    val actorData: ActorDetail? = null,
    val isFetchingDetails: Boolean = false,
)