package com.developersbreach.composeactors.data.trending.remote

import com.developersbreach.composeactors.core.network.BaseUrlProvider
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.person.model.Person
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendingApiImpl @Inject constructor(
    private val requestHandler: HttpRequestHandler
) : TrendingApi, BaseUrlProvider() {

    // trending/person/week?api_key=API_KEY
    override suspend fun getTrendingActors(): PagedResponse<Person> {
        return requestHandler.getPagedResponse<Person>(
            URL("${BASE_URL}trending/person/week?${API_KEY}")
        )
    }
}