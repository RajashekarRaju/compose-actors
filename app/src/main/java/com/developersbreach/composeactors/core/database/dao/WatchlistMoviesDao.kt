package com.developersbreach.composeactors.core.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developersbreach.composeactors.core.database.entity.WatchlistMoviesEntity

@Dao
interface WatchlistMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieToWatchlist(watchlistMoviesEntity: WatchlistMoviesEntity)

    @Query("SELECT * FROM watchlist_movies_table")
    fun getAllMoviesFromWatchlist(): LiveData<List<WatchlistMoviesEntity>>

    @Query("DELETE FROM watchlist_movies_table")
    fun deleteAllMoviesFromWatchlist()

    @Delete
    fun deleteSelectedMovieFromWatchlist(watchlistMoviesEntity: WatchlistMoviesEntity)

    @Query("SELECT column_movie_id FROM watchlist_movies_table WHERE column_movie_id = :movieId")
    fun checkIfMovieIsInWatchlist(movieId: Int): LiveData<Int>
}