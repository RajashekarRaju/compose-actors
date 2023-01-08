package com.developersbreach.composeactors.ui.screens.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.actor.ActorRepository

class NowPlayingMoviesPagingSource(
    private val actorRepository: ActorRepository
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPageNumber = params.key ?: 1

        val nowPlayingMoviesData: List<Movie> = actorRepository.getNowPlayingMoviesData(
            page = nextPageNumber
        )

        val nextKey = when {
            params.loadSize * (nextPageNumber + 1) < 16 -> nextPageNumber + 1
            else -> null
        }

        return LoadResult.Page(
            prevKey = null,
            nextKey = nextKey,
            data = nowPlayingMoviesData
        )
    }
}