package com.developersbreach.composeactors.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.developersbreach.composeactors.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DatabaseDataSource @Inject constructor(
    private val database: AppDatabase
) {

    fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        val allFavoriteMovies = database.favoriteMoviesDao.getAllFavoriteMovies()
        // Change to distinct until changed
        return Transformations.map(allFavoriteMovies) { favEntityList ->
            favEntityList.movieAsDomainModel()
        }
    }

    fun getAllFavoriteActors(): LiveData<List<Actor>> {
        val allFavoriteActors = database.favoriteActorsDao.getAllFavoriteActors()
        // Change to distinct until changed
        return Transformations.map(allFavoriteActors) { favEntityList ->
            favEntityList.actorAsDomainModel()
        }
    }

    fun checkIfMovieIsFavorite(
        movieId: Int
    ) = database.favoriteMoviesDao.checkIfMovieIsFavorite(movieId)

    fun checkIfActorIsFavorite(
        actorId: Int
    ) = database.favoriteActorsDao.checkIfActorIsFavorite(actorId)

    suspend fun addMovieToFavorites(
        movie: Movie
    ) = withContext(Dispatchers.IO) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.addMovieToFavorites(favoriteMoviesEntity = this)
        }
    }

    suspend fun addActorToFavorites(
        actor: Actor
    ) = withContext(Dispatchers.IO) {
        with(actor.actorAsDatabaseModel()) {
            database.favoriteActorsDao.addActorToFavorites(favoriteActorsEntity = this)
        }
    }

    suspend fun deleteSelectedFavoriteMovie(
        movie: Movie
    ) = withContext(Dispatchers.IO) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.deleteSelectedFavoriteMovie(favoriteMoviesEntity = this)
        }
    }

    suspend fun deleteSelectedFavoriteActor(
        actor: Actor
    ) = withContext(Dispatchers.IO) {
        with(actor.actorAsDatabaseModel()) {
            database.favoriteActorsDao.deleteSelectedFavoriteActor(favoriteActorsEntity = this)
        }
    }
}