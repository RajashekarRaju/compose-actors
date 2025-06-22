package com.developersbreach.composeactors.ui.screens.actorDetails

import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class ActorDetailsData(
    val castList: List<Movie> = listOf(),
    val actorData: PersonDetail? = null,
    val isFetchingDetails: Boolean = false,
    val isPersonInWatchlist: Flow<Boolean> = flow { emit(false) },
)

data class ActorDetailsSheetUIState(
    val selectedMovieDetails: MovieDetail? = null,
)