package com.developersbreach.composeactors.data.person.repository

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.person.remote.PersonApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class PersonRepositoryImpl @Inject constructor(
    private val personApi: PersonApi,
    private val databaseDataSource: DatabaseDataSource
) : PersonRepository {

    override suspend fun getPopularPersons(): List<Actor> = withContext(Dispatchers.IO) {
        personApi.getPopularPersons().data
    }

    override suspend fun getTrendingPersons(): List<Actor> = withContext(Dispatchers.IO) {
        personApi.getTrendingPersons().data
    }

    override suspend fun getPersonDetails(actorId: Int): ActorDetail = withContext(Dispatchers.IO) {
        personApi.getPersonDetails(actorId)
    }

    override suspend fun getCastDetails(actorId: Int): List<Movie> = withContext(Dispatchers.IO) {
        personApi.getCastDetails(actorId).movies
    }

    override fun isFavoritePerson(actorId: Int): LiveData<Int> {
        return databaseDataSource.checkIfActorIsFavorite(actorId)
    }

    override suspend fun addPersonToFavorite(favoriteActor: FavoriteActor) {
        databaseDataSource.addActorToFavorites(favoriteActor)
    }

    override suspend fun deleteSelectedFavoritePerson(favoriteActor: FavoriteActor) {
        databaseDataSource.deleteSelectedFavoriteActor(favoriteActor)
    }

    override fun getAllFavoritePersons(): LiveData<List<FavoriteActor>> {
        return databaseDataSource.getAllFavoriteActors()
    }
}