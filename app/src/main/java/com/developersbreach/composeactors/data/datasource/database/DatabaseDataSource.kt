package com.developersbreach.composeactors.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.model.actorAsDatabaseModel
import com.developersbreach.composeactors.data.model.actorAsDomainModel
import com.developersbreach.composeactors.data.model.movieAsDatabaseModel
import com.developersbreach.composeactors.data.model.movieAsDomainModel
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Singleton
class DatabaseDataSource @Inject constructor(
    private val database: AppDatabase
) {

    fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        val allFavoriteMovies = database.favoriteMoviesDao.getAllFavoriteMovies()
        // Change to distinct until changed
        return allFavoriteMovies.map { favEntityList ->
            favEntityList.movieAsDomainModel()
        }
    }

    fun getAllFavoriteActors(): LiveData<List<FavoriteActor>> {
        val allFavoriteActors = database.favoriteActorsDao.getAllFavoriteActors()
        // Change to distinct until changed
        return allFavoriteActors.map { favEntityList ->
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
        favoriteActor: FavoriteActor
    ) = withContext(Dispatchers.IO) {
        with(favoriteActor.actorAsDatabaseModel()) {
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
        favoriteActor: FavoriteActor
    ) = withContext(Dispatchers.IO) {
        with(favoriteActor.actorAsDatabaseModel()) {
            database.favoriteActorsDao.deleteSelectedFavoriteActor(favoriteActorsEntity = this)
        }
    }
}