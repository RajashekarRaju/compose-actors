package com.developersbreach.composeactors.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developersbreach.composeactors.repository.database.entity.FavoriteMoviesEntity

@Dao
interface FavoriteMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieToFavorites(favoriteMoviesEntity: FavoriteMoviesEntity)

    @Query("SELECT * FROM favorite_movies_table")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMoviesEntity>>

    @Query("DELETE FROM favorite_movies_table")
    fun deleteAllFavoriteMovies()

    @Delete
    fun deleteSelectedFavoriteMovie(favoriteMoviesEntity: FavoriteMoviesEntity)
}