package com.developersbreach.composeactors.data.thumbnail.remote

import com.developersbreach.composeactors.core.network.HttpRequestHandler
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YouTubeThumbnailProviderApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler
) : YouTubeThumbnailProviderApi {

    // SUXWAEX2jlg
    override suspend fun getMovieTrailerThumbnail(trailerId: String) {
        return requestHandler.getResponse(
            URL("https://img.youtube.com/vi/$trailerId/sddefault.jpg")
        )
    }
}