package com.developersbreach.composeactors.data.movie.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.repository.MovieRepository

class MoviesPagingSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val currentPageNumber = params.key ?: 1
        return loadMovies(currentPageNumber, params)
    }

    private suspend fun loadMovies(
        currentPageNumber: Int,
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        val response = movieRepository.getNowPlayingMovies(currentPageNumber)
        return response.fold(
            ifLeft = { LoadResult.Error(it) },
            ifRight = {
                val nextKey = when {
                    (params.loadSize * (currentPageNumber + 1)) < it.totalPages -> currentPageNumber + 1
                    else -> null
                }
                LoadResult.Page(
                    prevKey = null,
                    nextKey = nextKey,
                    data = it.data
                )
            }
        )
    }
}