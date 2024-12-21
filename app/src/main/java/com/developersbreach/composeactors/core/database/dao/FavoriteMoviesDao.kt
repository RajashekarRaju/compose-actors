package com.developersbreach.composeactors.core.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developersbreach.composeactors.core.database.entity.FavoriteMoviesEntity

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

    @Query("SELECT column_movie_id FROM favorite_movies_table WHERE column_movie_id = :movieId")
    fun checkIfMovieIsFavorite(movieId: Int): LiveData<Int>
}