package com.developersbreach.composeactors.data.datasource.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.developersbreach.composeactors.data.datasource.database.entity.UserRegionEntity

@Dao
interface UserRegionDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun editUserRegion(userRegionEntity: UserRegionEntity)

    @Query("SELECT * FROM user_region_table")
    fun getSavedUserRegion(): LiveData<UserRegionEntity>
}