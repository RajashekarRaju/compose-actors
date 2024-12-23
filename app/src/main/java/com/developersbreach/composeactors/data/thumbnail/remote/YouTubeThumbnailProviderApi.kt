package com.developersbreach.composeactors.data.thumbnail.remote

import arrow.core.Either

interface YouTubeThumbnailProviderApi {
    suspend fun getMovieTrailerThumbnail(trailerId: String): Either<Throwable, Unit>
}