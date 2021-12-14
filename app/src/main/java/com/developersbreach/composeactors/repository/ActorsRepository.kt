package com.developersbreach.composeactors.repository

import com.developersbreach.composeactors.data.NetworkService
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActorsRepository {

    private val networkService = NetworkService()

    suspend fun getPopularActorsData(): List<Actor> {
        val listData: List<Actor>
        withContext(Dispatchers.IO) {
            listData = networkService.getPopularActors()
        }
        return listData
    }

    suspend fun getTrendingActorsData(): List<Actor> {
        val listData: List<Actor>
        withContext(Dispatchers.IO) {
            listData = networkService.getTrendingActors()
        }
        return listData
    }

    suspend fun getSelectedActorData(
        actorId: Int
    ): ActorDetail {
        val selectedData: ActorDetail
        withContext(Dispatchers.IO) {
            selectedData = networkService.getActorDetails(actorId)
        }
        return selectedData
    }

    suspend fun getCastData(
        actorId: Int
    ): List<Movie> {
        val listData: List<Movie>
        withContext(Dispatchers.IO) {
            listData = networkService.getCastDetails(actorId)
        }
        return listData
    }

    suspend fun getSearchableActorsData(
        query: String
    ): List<Actor> {
        val listData: List<Actor>
        withContext(Dispatchers.IO) {
            listData = networkService.getSearchableActors(query)
        }
        return listData
    }
}