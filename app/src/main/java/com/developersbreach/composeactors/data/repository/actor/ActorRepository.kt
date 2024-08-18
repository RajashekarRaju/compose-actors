package com.developersbreach.composeactors.data.repository.actor

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.datasource.network.NetworkDataSource
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.user.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActorRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
    private val userRepository: UserRepository
) {
    suspend fun getPopularActorsData(): List<Actor> {
        return networkDataSource.getPopularActorsData(userRepository.getRegion())
    }

    suspend fun getTrendingActorsData(): List<Actor> {
        return networkDataSource.getTrendingActorsData(userRepository.getRegion())
    }

    suspend fun getUpcomingMoviesData(): List<Movie> {
        return networkDataSource.getUpcomingMoviesData(userRepository.getRegion())
    }

    suspend fun getSelectedActorData(actorInt: Int): ActorDetail {
        return networkDataSource.getSelectedActorData(actorInt)
    }

    suspend fun getCastData(actorInt: Int): List<Movie> {
        return networkDataSource.getCastData(actorInt)
    }

    fun isFavoriteActor(actorId: Int): LiveData<Int> {
        return databaseDataSource.checkIfActorIsFavorite(actorId)
    }

    suspend fun addActorsToFavorite(favoriteActor: FavoriteActor) {
        databaseDataSource.addActorToFavorites(favoriteActor)
    }

    suspend fun deleteSelectedFavoriteActor(favoriteActor: FavoriteActor) {
        databaseDataSource.deleteSelectedFavoriteActor(favoriteActor)
    }

    fun getAllFavoriteActors(): LiveData<List<FavoriteActor>> {
        return databaseDataSource.getAllFavoriteActors()
    }
}