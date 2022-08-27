package com.developersbreach.composeactors.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.developersbreach.composeactors.data.convertor.actorAsDatabaseModel
import com.developersbreach.composeactors.data.convertor.actorAsDomainModel
import com.developersbreach.composeactors.data.convertor.movieAsDatabaseModel
import com.developersbreach.composeactors.data.convertor.movieAsDomainModel
import com.developersbreach.composeactors.data.database.*
import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.Movie

class DatabaseDataSource(
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
    ): LiveData<Int> {
        return database.favoriteMoviesDao.checkIfMovieIsFavorite(movieId)
    }

    fun checkIfActorIsFavorite(
        actorId: Int
    ): LiveData<Int> {
        return database.favoriteActorsDao.checkIfActorIsFavorite(actorId)
    }

    fun addMovieToFavorites(
        movie: Movie
    ) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.addMovieToFavorites(favoriteMoviesEntity = this)
        }
    }

    fun addActorToFavorites(
        actor: Actor
    ) {
        with(actor.actorAsDatabaseModel()) {
            database.favoriteActorsDao.addActorToFavorites(favoriteActorsEntity = this)
        }
    }

    fun deleteSelectedFavoriteMovie(
        movie: Movie
    ) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.deleteSelectedFavoriteMovie(favoriteMoviesEntity = this)
        }
    }

    fun deleteSelectedFavoriteActor(
        actor: Actor
    ) {
        with(actor.actorAsDatabaseModel()) {
            database.favoriteActorsDao.deleteSelectedFavoriteActor(favoriteActorsEntity = this)
        }
    }
}