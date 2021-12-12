package com.developersbreach.composeactors.repository

import com.developersbreach.composeactors.model.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActorsRepository {

    suspend fun getActorsData(): List<Actor> {
        val listData: List<Actor>
        withContext(Dispatchers.IO) {
            listData = NetworkService().getActors()
        }
        return listData
    }
}