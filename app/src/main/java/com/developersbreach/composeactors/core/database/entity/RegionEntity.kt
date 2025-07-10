package com.developersbreach.composeactors.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.developersbreach.composeactors.data.region.model.Region

@Entity(tableName = "region_table")
data class RegionEntity(
    @PrimaryKey
    @ColumnInfo(name = "region_id")
    val id: Int = 0,
    @ColumnInfo(name = "region_code")
    val regionCode: String,
    @ColumnInfo(name = "region_name")
    val regionName: String,
)

fun RegionEntity.toRegion() = Region(
    code = regionCode,
    name = regionName,
)