package com.developersbreach.composeactors.data.trending.repository

import arrow.core.Either
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.trending.remote.TrendingApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class TrendingRepositoryImpl @Inject constructor(
    private val trendingApi: TrendingApi
) : TrendingRepository {

    override suspend fun getTrendingActors(): Either<Throwable, List<Person>> = withContext(
        Dispatchers.IO
    ) {
        trendingApi.getTrendingActors().map {
            it.data
        }
    }
}