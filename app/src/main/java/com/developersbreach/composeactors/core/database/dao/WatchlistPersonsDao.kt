package com.developersbreach.composeactors.core.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developersbreach.composeactors.core.database.entity.WatchlistPersonsEntity

@Dao
interface WatchlistPersonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPersonToWatchlist(watchlistPersonsEntity: WatchlistPersonsEntity)

    @Query("SELECT * FROM favorite_persons_table")
    fun getAllPersonsFromWatchlist(): LiveData<List<WatchlistPersonsEntity>>

    @Query("DELETE FROM favorite_persons_table")
    fun deleteAllPersonsFromWatchlist()

    @Delete
    fun deleteSelectedPersonFromWatchlist(watchlistPersonsEntity: WatchlistPersonsEntity)

    @Query("SELECT column_person_id FROM favorite_persons_table WHERE column_person_id = :personId")
    fun checkIfPersonIsInWatchlist(personId: Int): LiveData<Int>
}