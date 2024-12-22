package com.developersbreach.composeactors.data.person.repository

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie

interface PersonRepository {
    suspend fun getPopularPersons(): List<Actor>
    suspend fun getTrendingPersons(): List<Actor>
    suspend fun getPersonDetails(actorId: Int): ActorDetail
    suspend fun getCastDetails(actorId: Int): List<Movie>
    fun isFavoritePerson(actorId: Int): LiveData<Int>
    suspend fun addPersonToFavorite(favoriteActor: FavoriteActor)
    suspend fun deleteSelectedFavoritePerson(favoriteActor: FavoriteActor)
    fun getAllFavoritePersons(): LiveData<List<FavoriteActor>>
}