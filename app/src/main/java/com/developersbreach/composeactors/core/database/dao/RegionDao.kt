package com.developersbreach.composeactors.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.developersbreach.composeactors.core.database.entity.RegionEntity

@Dao
interface RegionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setRegion(regionEntity: RegionEntity)

    @Query("SELECT * FROM region_table WHERE region_id = 0")
    suspend fun getRegion(): RegionEntity?
}