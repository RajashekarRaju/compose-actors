package com.developersbreach.composeactors.data.region.model

import com.developersbreach.composeactors.core.database.entity.RegionEntity
import java.util.Locale

data class Region(
    val code: String,
    val name: String,
) {
    companion object {
        val defaultRegion = Region(
            code = "US",
            name = Locale("", "US").getDisplayCountry(Locale.getDefault()),
        )
    }
}

fun Region.toEntity() = RegionEntity(
    regionCode = code,
    regionName = name,
)