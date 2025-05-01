package com.developersbreach.composeactors.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developersbreach.composeactors.core.database.entity.SessionEntity

@Dao
interface SessionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGuest(sessionEntity: SessionEntity)

    @Query("SELECT column_is_guest FROM session_table WHERE column_session_id = 0")
    suspend fun isGuest(): Boolean
}