package com.developersbreach.composeactors.data.watchlist.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WatchlistMoviesRemoteKeysDao {
    @Query("SELECT * FROM watchlist_movie_remote_keys_table WHERE movie_id = :movieId")
    suspend fun remoteKeysByMovieId(movieId: Int): WatchlistMoviesRemoteKeysEntity?

    @Query("DELETE FROM watchlist_movie_remote_keys_table WHERE movie_id = :movieId")
    suspend fun deleteByMovieId(movieId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<WatchlistMoviesRemoteKeysEntity>)

    @Query("DELETE FROM watchlist_movie_remote_keys_table")
    suspend fun clearRemoteKeys()
}