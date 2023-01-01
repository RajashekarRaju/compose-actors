package com.developersbreach.composeactors.data.repository.actor

import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.NetworkRepository
import com.developersbreach.composeactors.data.repository.search.SearchRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActorRepository @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val searchRepository: SearchRepository
) {
    suspend fun getPopularActorsData(): List<Actor> {
        return networkRepository.getPopularActorsData()
    }

    suspend fun getTrendingActorsData(): List<Actor> {
        return networkRepository.getTrendingActorsData()
    }

    suspend fun getUpcomingMoviesData(): List<Movie> {
        return networkRepository.getUpcomingMoviesData()
    }

    suspend fun getNowPlayingMoviesData(): List<Movie> {
        return networkRepository.getNowPlayingMoviesData()
    }

    suspend fun getSelectedActorData(actorInt: Int): ActorDetail {
        return networkRepository.getSelectedActorData(actorInt)
    }

    suspend fun getCastData(actorInt: Int): List<Movie> {
        return networkRepository.getCastData(actorInt)
    }

    suspend fun getSearchableActorsData(searchQuery: String): List<Actor> {
        return searchRepository.getSearchableActorsData(searchQuery)
    }
}