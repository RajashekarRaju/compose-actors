package com.developersbreach.composeactors.data.trending.repository

import com.developersbreach.composeactors.data.model.Actor

interface TrendingRepository {
    suspend fun getTrendingActors(): List<Actor>
}