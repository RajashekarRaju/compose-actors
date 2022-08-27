package com.developersbreach.composeactors.repository

import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.datasource.DatabaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DatabaseRepository(
    private val dataSource: DatabaseDataSource
) {

    fun getAllFavoriteMovies() = dataSource.getAllFavoriteMovies()

    fun getAllFavoriteActors() = dataSource.getAllFavoriteActors()

    fun checkIfMovieIsFavorite(
        movieId: Int
    ) = dataSource.checkIfMovieIsFavorite(movieId)

    fun checkIfActorIsFavorite(
        actorId: Int
    ) = dataSource.checkIfActorIsFavorite(actorId)

    suspend fun addMovieToFavorites(
        movie: Movie
    ) = withContext(Dispatchers.IO) {
        dataSource.addMovieToFavorites(movie)
    }

    suspend fun addActorToFavorites(
        actor: Actor
    ) = withContext(Dispatchers.IO) {
        dataSource.addActorToFavorites(actor)
    }

    suspend fun deleteSelectedFavoriteMovie(
        movie: Movie
    ) = withContext(Dispatchers.IO) {
        dataSource.deleteSelectedFavoriteMovie(movie)
    }

    suspend fun deleteSelectedFavoriteActor(
        actor: Actor
    ) = withContext(Dispatchers.IO) {
        dataSource.deleteSelectedFavoriteActor(actor)
    }
}