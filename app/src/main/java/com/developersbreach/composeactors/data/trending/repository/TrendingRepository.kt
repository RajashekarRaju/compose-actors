package com.developersbreach.composeactors.data.trending.repository

import com.developersbreach.composeactors.data.person.model.Person

interface TrendingRepository {
    suspend fun getTrendingActors(): List<Person>
}