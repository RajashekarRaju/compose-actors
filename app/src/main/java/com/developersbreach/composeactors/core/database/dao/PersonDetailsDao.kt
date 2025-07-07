package com.developersbreach.composeactors.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity

@Dao
interface PersonDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonDetail(personDetail: PersonDetailEntity)

    @Query("SELECT * FROM person_detail_table WHERE column_person_detail_id = :personId")
    suspend fun getPersonDetail(personId: Int): PersonDetailEntity?
}