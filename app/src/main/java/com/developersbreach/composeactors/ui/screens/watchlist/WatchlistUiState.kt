package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.paging.PagingData
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import com.developersbreach.composeactors.ui.components.CommonUiMessage
import com.developersbreach.composeactors.ui.components.ErrorMessage
import com.developersbreach.composeactors.ui.components.UiMessageWithResId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WatchlistUiState(
    val watchlistMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val watchlistPersons: Flow<PagingData<WatchlistPerson>> = emptyFlow(),
)

sealed class WatchlistUiMessage : ErrorMessage {
    data object WatchlistError : WatchlistUiMessage()

    data class ValidationError(val message: String) : WatchlistUiMessage()

    data class Common(val error: CommonUiMessage) : WatchlistUiMessage()

    override fun toUiMessageWithResId(): UiMessageWithResId = when (this) {
        is WatchlistError -> UiMessageWithResId(R.string.watchlist_error)
        is ValidationError -> UiMessageWithResId(R.string.watchlist_validation_error, listOf(message))
        is Common -> error.toUiMessageWithResId()
    }
}