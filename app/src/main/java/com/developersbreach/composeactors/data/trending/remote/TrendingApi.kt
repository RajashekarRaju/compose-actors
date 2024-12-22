package com.developersbreach.composeactors.data.trending.remote

import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.person.model.Person

interface TrendingApi {
    suspend fun getTrendingActors(): PagedResponse<Person>
}