package com.developersbreach.composeactors.data.trending.repository

import com.developersbreach.composeactors.data.model.Person

interface TrendingRepository {
    suspend fun getTrendingActors(): List<Person>
}