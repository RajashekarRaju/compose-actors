package com.developersbreach.composeactors.data.repository.movie

import com.developersbreach.composeactors.data.datasource.network.JsonRemoteData
import com.developersbreach.composeactors.data.datasource.network.RequestUrls
import com.developersbreach.composeactors.utils.NetworkQueryUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieTrailerRepository @Inject constructor(
    private val requestUrls: RequestUrls,
    private val jsonData: JsonRemoteData,
    private val queryUtils: NetworkQueryUtils
) {

    suspend fun getMovieTrailerUrl(
        movieId: Int
    ) {
        withContext(Dispatchers.IO) {
            val requestUrl = requestUrls.getMovieTrailerUrl(movieId)
            val response = queryUtils.getResponseFromHttpUrl(requestUrl)

            // Watching trailer for movies needs to implemented
            // Planned for v0.5.0
        }
    }

    suspend fun getMovieTrailerThumbnail(
        trailerId: String
    ) {
        withContext(Dispatchers.IO) {
            val requestUrl = requestUrls.buildMovieTrailerThumbnail(trailerId)
            val response = queryUtils.getResponseFromHttpUrl(requestUrl)

            // Watching trailer for movies needs to implemented
            // Planned for v0.5.0
        }
    }
}