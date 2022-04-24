package com.developersbreach.composeactors.repository.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DatabaseRepository(
    private val database: FavoritesDatabase
) {

    suspend fun addActorToFavorites(
        actor: Actor
    ) {
        withContext(Dispatchers.IO) {
            with(actor.actorAsDatabaseModel()) {
                database.favoriteActorsDao.addActorToFavorites(favoriteActorsEntity = this)
            }
        }
    }

    fun checkIfActorIsFavorite(
        actorId: Int
    ): LiveData<Int> {
        return database.favoriteActorsDao.checkIfActorIsFavorite(actorId)
    }

    fun getAllFavoriteActors(): LiveData<List<Actor>> {
        val allFavoriteActors = database.favoriteActorsDao.getAllFavoriteActors()
        // Change to distinct until changed
        return Transformations.map(allFavoriteActors) { favEntityList ->
            favEntityList.actorAsDomainModel()
        }
    }

    suspend fun deleteSelectedFavoriteActor(
        actor: Actor
    ) {
        withContext(Dispatchers.IO) {
            with(actor.actorAsDatabaseModel()) {
                database.favoriteActorsDao.deleteSelectedFavoriteActor(favoriteActorsEntity = this)
            }
        }
    }

    suspend fun addMovieToFavorites(
        movie: Movie
    ) {
        withContext(Dispatchers.IO) {
            with(movie.movieAsDatabaseModel()) {
                database.favoriteMoviesDao.addMovieToFavorites(favoriteMoviesEntity = this)
            }
        }
    }

    fun checkIfMovieIsFavorite(
        movieId: Int
    ): LiveData<Int> {
        return database.favoriteMoviesDao.checkIfMovieIsFavorite(movieId)
    }

    fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        val allFavoriteMovies = database.favoriteMoviesDao.getAllFavoriteMovies()
        // Change to distinct until changed
        return Transformations.map(allFavoriteMovies) { favEntityList ->
            favEntityList.movieAsDomainModel()
        }
    }

    suspend fun deleteSelectedFavoriteMovie(
        movie: Movie
    ) {
        withContext(Dispatchers.IO) {
            with(movie.movieAsDatabaseModel()) {
                database.favoriteMoviesDao.deleteSelectedFavoriteMovie(favoriteMoviesEntity = this)
            }
        }
    }
}