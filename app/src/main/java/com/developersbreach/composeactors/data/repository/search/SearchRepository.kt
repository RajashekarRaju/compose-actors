package com.developersbreach.composeactors.data.repository.search

import com.developersbreach.composeactors.data.datasource.network.JsonRemoteData
import com.developersbreach.composeactors.data.datasource.network.RequestUrls
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.utils.NetworkQueryUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val requestUrls: RequestUrls,
    private val jsonData: JsonRemoteData,
    private val queryUtils: NetworkQueryUtils
) {

    /**
     * @param query user submitted query to search actors.
     * @return the result of list of actors with matching query fetched from the network.
     */
    suspend fun getSearchableActorsData(
        query: String
    ): List<Actor> = withContext(Dispatchers.IO) {
        val requestUrl = requestUrls.getSearchActorsUrl(query)
        val response = queryUtils.getResponseFromHttpUrl(requestUrl)
        jsonData.fetchActorsJsonData(response)
    }
}