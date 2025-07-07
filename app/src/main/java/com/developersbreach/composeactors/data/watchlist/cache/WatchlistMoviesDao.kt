package com.developersbreach.composeactors.data.watchlist.cache

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToWatchlist(watchlistMovieEntity: WatchlistMovieEntity)

    @Query("SELECT * FROM watchlist_movies_table ORDER BY movie_id ASC")
    fun getAllMoviesFromWatchlist(): PagingSource<Int, WatchlistMovieEntity>

    @Query("DELETE FROM watchlist_movies_table")
    suspend fun deleteAllMoviesInWatchlist()

    @Delete
    suspend fun deleteMovieFromWatchlist(watchlistMovieEntity: WatchlistMovieEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist_movies_table WHERE movie_id = :movieId)")
    fun isMovieInWatchlist(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMoviesToWatchlist(movies: List<WatchlistMovieEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist_movies_table)")
    suspend fun existsAny(): Boolean
}