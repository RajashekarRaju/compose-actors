package com.developersbreach.composeactors.data.datasource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_region_table")
data class UserRegionEntity(

    @PrimaryKey
    @ColumnInfo(name = "user_region_country_code")
    val countryCode: Int,

    @ColumnInfo(name = "user_region_country_name")
    val countryName: String
)