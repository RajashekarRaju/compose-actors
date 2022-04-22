package com.developersbreach.composeactors.repository.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.model.Movie


class DatabaseRepository(
    private val database: FavoritesDatabase
) {

    suspend fun addActorToFavorites(
        actor: Actor
    ) {
        with(actor.actorAsDatabaseModel()) {
            database.favoriteActorsDao.addActorToFavorites(favoriteActorsEntity = this)
        }
    }

    suspend fun getAllFavoriteActors(): LiveData<List<Actor>> {
        val allFavoriteActors = database.favoriteActorsDao.getAllFavoriteActors()
        // Change to distinct until changed
        return Transformations.map(allFavoriteActors) { favEntityList ->
            favEntityList.actorAsDomainModel()
        }
    }

    suspend fun deleteAllFavoriteActors() {
        database.favoriteActorsDao.deleteAllFavoriteActors()
    }

    suspend fun deleteSelectedFavoriteActor(
        actor: Actor
    ) {
        with(actor.actorAsDatabaseModel()) {
            database.favoriteActorsDao.deleteSelectedFavoriteActor(favoriteActorsEntity = this)
        }
    }

    suspend fun addMovieToFavorites(
        movie: Movie
    ) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.addMovieToFavorites(favoriteMoviesEntity = this)
        }
    }

    suspend fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        val allFavoriteMovies = database.favoriteMoviesDao.getAllFavoriteMovies()
        // Change to distinct until changed
        return Transformations.map(allFavoriteMovies) { favEntityList ->
            favEntityList.movieAsDomainModel()
        }
    }

    suspend fun deleteAllFavoriteMovies() {
        database.favoriteMoviesDao.deleteAllFavoriteMovies()
    }

    suspend fun deleteSelectedFavoriteMovie(
        movie: Movie
    ) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.deleteSelectedFavoriteMovie(favoriteMoviesEntity = this)
        }
    }
}