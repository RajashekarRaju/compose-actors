package com.developersbreach.composeactors.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.core.database.entity.movieAsDomainModel
import com.developersbreach.composeactors.core.database.entity.toFavoritePersons
import com.developersbreach.composeactors.data.person.model.FavoritePerson
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.model.FavoritePersonsEntity
import com.developersbreach.composeactors.data.movie.model.movieAsDatabaseModel
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.model.toEntity
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
        return allFavoriteMovies.map { favEntityList ->
            favEntityList.movieAsDomainModel()
        }
    }

    fun getAllFavoritePersons(): LiveData<List<FavoritePerson>> {
        val allFavoritePersons = database.favoritePersonsDao.getAllFavoritePersons()
        return allFavoritePersons.map { favEntityList ->
            favEntityList.toFavoritePersons()
        }
    }

    fun checkIfMovieIsFavorite(
        movieId: Int
    ) = database.favoriteMoviesDao.checkIfMovieIsFavorite(movieId)

    fun checkIfPersonIsFavorite(
        personId: Int
    ) = database.favoritePersonsDao.checkIfPersonIsFavorite(personId)

    suspend fun addMovieToFavorites(
        movie: Movie
    ) = withContext(Dispatchers.IO) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.addMovieToFavorites(favoriteMoviesEntity = this)
        }
    }

    suspend fun addPersonToFavorites(
        favoritePerson: FavoritePerson
    ) = withContext(Dispatchers.IO) {
        with(favoritePerson.FavoritePersonsEntity()) {
            database.favoritePersonsDao.addPersonToFavorites(favoritePersonsEntity = this)
        }
    }

    suspend fun deleteSelectedFavoriteMovie(
        movie: Movie
    ) = withContext(Dispatchers.IO) {
        with(movie.movieAsDatabaseModel()) {
            database.favoriteMoviesDao.deleteSelectedFavoriteMovie(favoriteMoviesEntity = this)
        }
    }

    suspend fun deleteSelectedFavoritePerson(
        favoritePerson: FavoritePerson
    ) = withContext(Dispatchers.IO) {
        with(favoritePerson.FavoritePersonsEntity()) {
            database.favoritePersonsDao.deleteSelectedFavoritePerson(favoritePersonsEntity = this)
        }
    }
}