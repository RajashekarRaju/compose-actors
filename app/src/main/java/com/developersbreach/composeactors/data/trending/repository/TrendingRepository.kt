package com.developersbreach.composeactors.data.trending.repository

import arrow.core.Either
import com.developersbreach.composeactors.data.person.model.Person

interface TrendingRepository {
    suspend fun getTrendingActors(): Either<Throwable, List<Person>>
}