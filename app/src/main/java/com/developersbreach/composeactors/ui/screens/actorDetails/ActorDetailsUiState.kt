package com.developersbreach.composeactors.ui.screens.actorDetails

import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class ActorDetailsUiState(
    val castList: List<Movie> = listOf(),
    val actorData: PersonDetail? = null,
    val isFetchingDetails: Boolean = false,
    val isPersonInWatchlist: Flow<Boolean> = flow { emit(false) },
    val selectedMovieDetails: MovieDetail? = null,
)

sealed class ActorDetailsUiMessage : ErrorMessage {
    data object ActorDetailsError : ActorDetailsUiMessage()

    data class ValidationError(val message: String) : ActorDetailsUiMessage()

    data class Common(val error: CommonUiMessage) : ActorDetailsUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is ActorDetailsError -> UiMessageWithResId(R.string.actor_details_error)
        is ValidationError -> UiMessageWithResId(R.string.actor_details_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}