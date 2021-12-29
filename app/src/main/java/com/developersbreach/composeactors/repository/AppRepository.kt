package com.developersbreach.composeactors.repository

import com.developersbreach.composeactors.data.NetworkDataSource
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Functions in this repository executes on an IO-optimized thread pool, makes main-safe.
 * Repository class executes network calls from [NetworkDataSource] to return data.
 * Data returned from this functions will be exposed to ViewModels.
 */
class AppRepository(
    private val source: NetworkDataSource
) {

    // Suspend function executes network call.
    suspend fun getPopularActorsData(): List<Actor> {
        val popularActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            popularActorsList = source.getPopularActors()
        }
        return popularActorsList
    }

    // Suspend function executes network call.
    suspend fun getTrendingActorsData(): List<Actor> {
        val trendingActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            trendingActorsList = source.getTrendingActors()
        }
        return trendingActorsList
    }

    // Suspend function executes network call.
    suspend fun getSelectedActorData(
        actorId: Int
    ): ActorDetail {
        val selectedActorDetails: ActorDetail
        withContext(Dispatchers.IO) {
            selectedActorDetails = source.getActorDetails(actorId)
        }
        return selectedActorDetails
    }

    // Suspend function executes network call.
    suspend fun getCastData(
        actorId: Int
    ): List<Movie> {
        val castListData: List<Movie>
        withContext(Dispatchers.IO) {
            castListData = source.getCastDetails(actorId)
        }
        return castListData
    }

    // Suspend function executes network call.
    suspend fun getSearchableActorsData(
        query: String
    ): List<Actor> {
        val searchableActorsList: List<Actor>
        withContext(Dispatchers.IO) {
            searchableActorsList = source.getSearchableActors(query)
        }
        return searchableActorsList
    }
}