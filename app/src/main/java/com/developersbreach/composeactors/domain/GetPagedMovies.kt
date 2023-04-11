package com.developersbreach.composeactors.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.movie.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetPagedMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(
        viewModelScope: CoroutineScope
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            initialKey = INITIAL_PAGE_KEY,
            pagingSourceFactory = {
                MoviesPagingSource(movieRepository)
            }
        ).flow.cachedIn(viewModelScope)
    }

    companion object {
        private const val INITIAL_PAGE_KEY = 1
        private const val PAGE_SIZE = 20
    }
}