package com.developersbreach.composeactors.data.watchlist.cache

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistPersonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPersonToWatchlist(watchlistPersonsEntity: WatchlistPersonEntity)

    @Query("SELECT * FROM watchlist_people_table ORDER BY person_id ASC")
    fun getPeopleFromWatchlist(): PagingSource<Int, WatchlistPersonEntity>

    @Query("DELETE FROM watchlist_people_table")
    fun deletePeopleFromWatchlist()

    @Query("DELETE FROM watchlist_people_table WHERE person_id = :personId")
    suspend fun deletePersonFromWatchlist(personId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist_people_table WHERE person_id = :personId)")
    fun isPersonInWatchlist(personId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPeopleToWatchlist(people: List<WatchlistPersonEntity>)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist_people_table)")
    suspend fun existsAny(): Boolean
}