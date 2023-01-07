package com.developersbreach.composeactors.data.repository.movie

import com.developersbreach.composeactors.data.datasource.network.NetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieTrailerRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {

    suspend fun getMovieTrailerUrl(movieId: Int) {
        networkDataSource.getMovieTrailerUrl(movieId)
    }

    suspend fun getMovieTrailerThumbnail(trailerId: String) {
        networkDataSource.getMovieTrailerThumbnail(trailerId)
    }
}