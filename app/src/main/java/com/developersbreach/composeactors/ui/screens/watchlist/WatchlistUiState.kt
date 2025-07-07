package com.developersbreach.composeactors.ui.screens.watchlist

import androidx.paging.PagingData
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WatchlistUiState(
    val watchlistMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val watchlistPersons: Flow<PagingData<WatchlistPerson>> = emptyFlow(),
)