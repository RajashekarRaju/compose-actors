package com.developersbreach.composeactors.ui.screens.movieDetail

import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class MovieDetailsUiState(
    val movieData: MovieDetail? = null,
    val similarMovies: List<Movie> = emptyList(),
    val recommendedMovies: List<Movie> = emptyList(),
    val movieCast: List<Cast> = emptyList(),
    val isMovieInWatchlist: Flow<Boolean> = flow { emit(false) },
    val movieProviders: List<Flatrate> = emptyList(),
    val selectedPersonDetails: PersonDetail? = null,
    val selectedMovieDetails: MovieDetail? = null,
)

sealed class MovieDetailsUiMessage : ErrorMessage {
    data object MovieDetailsError : MovieDetailsUiMessage()

    data class ValidationError(val message: String) : MovieDetailsUiMessage()

    data class Common(val error: CommonUiMessage) : MovieDetailsUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is MovieDetailsError -> UiMessageWithResId(R.string.movie_details_error)
        is ValidationError -> UiMessageWithResId(R.string.movie_details_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}