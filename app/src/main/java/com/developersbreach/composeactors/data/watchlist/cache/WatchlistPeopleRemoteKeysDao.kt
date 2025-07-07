package com.developersbreach.composeactors.data.watchlist.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WatchlistPeopleRemoteKeysDao {
    @Query("SELECT * FROM watchlist_person_remote_keys_table WHERE person_id = :personId")
    suspend fun remoteKeysByPersonId(personId: Int): WatchlistPersonRemoteKeysEntity?

    @Query("DELETE FROM watchlist_person_remote_keys_table WHERE person_id = :personId")
    suspend fun deleteByPersonId(personId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<WatchlistPersonRemoteKeysEntity>)

    @Query("DELETE FROM watchlist_person_remote_keys_table")
    suspend fun clearRemoteKeys()
}