package com.developersbreach.composeactors.data.trending.repository

import com.developersbreach.composeactors.data.model.Person
import com.developersbreach.composeactors.data.trending.remote.TrendingApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class TrendingRepositoryImpl @Inject constructor(
    private val trendingApi: TrendingApi,
) : TrendingRepository {

    override suspend fun getTrendingActors(): List<Person> = withContext(Dispatchers.IO) {
        trendingApi.getTrendingActors().data
    }
}