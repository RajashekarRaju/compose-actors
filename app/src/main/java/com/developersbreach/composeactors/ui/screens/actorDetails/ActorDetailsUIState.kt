package com.developersbreach.composeactors.ui.screens.actorDetails

import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail

data class ActorDetailsData(
    val castList: List<Movie> = listOf(),
    val actorData: PersonDetail? = null,
    val isFetchingDetails: Boolean = false,
)

data class ActorDetailsSheetUIState(
    val selectedMovieDetails: MovieDetail? = null
)