package com.developersbreach.composeactors.domain.watchlist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.watchlist.paging.WatchlistMoviePagingSource
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetPagedWatchlistMovies @Inject constructor(
    private val watchlistRepository: WatchlistRepository,
) {

    operator fun invoke(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            initialKey = INITIAL_PAGE_KEY,
            pagingSourceFactory = {
                WatchlistMoviePagingSource(watchlistRepository)
            },
        ).flow
    }

    companion object {
        private const val INITIAL_PAGE_KEY = 0
        private const val PAGE_SIZE = 10
    }
}