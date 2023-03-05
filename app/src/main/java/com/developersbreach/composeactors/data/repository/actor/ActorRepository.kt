package com.developersbreach.composeactors.data.repository.actor

import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.datasource.network.NetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActorRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getPopularActorsData(): List<Actor> {
        return networkDataSource.getPopularActorsData()
    }

    suspend fun getTrendingActorsData(): List<Actor> {
        return networkDataSource.getTrendingActorsData()
    }

    suspend fun getUpcomingMoviesData(): List<Movie> {
        return networkDataSource.getUpcomingMoviesData()
    }

    suspend fun getSelectedActorData(actorInt: Int): ActorDetail {
        return networkDataSource.getSelectedActorData(actorInt)
    }

    suspend fun getCastData(actorInt: Int): List<Movie> {
        return networkDataSource.getCastData(actorInt)
    }
}