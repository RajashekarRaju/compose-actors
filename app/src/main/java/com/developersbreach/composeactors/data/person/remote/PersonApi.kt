package com.developersbreach.composeactors.data.person.remote

import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.MoviesResponse

interface PersonApi {
    suspend fun getPopularPersons(): PagedResponse<Actor>
    suspend fun getTrendingPersons(): PagedResponse<Actor>
    suspend fun getPersonDetails(actorId: Int): ActorDetail
    suspend fun getCastDetails(actorId: Int): MoviesResponse
}