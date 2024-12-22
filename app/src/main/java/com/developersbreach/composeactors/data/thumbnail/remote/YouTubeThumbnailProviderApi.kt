package com.developersbreach.composeactors.data.thumbnail.remote

interface YouTubeThumbnailProviderApi {
    suspend fun getMovieTrailerThumbnail(trailerId: String)
}