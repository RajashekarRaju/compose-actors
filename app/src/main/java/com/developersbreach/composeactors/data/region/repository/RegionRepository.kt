package com.developersbreach.composeactors.data.region.repository

import arrow.core.Either
import com.developersbreach.composeactors.data.region.model.Region

interface RegionRepository {
    fun getRegions(): Either<Throwable, List<Region>>

    suspend fun getRegion(): Either<Throwable, Region>

    suspend fun updateRegion(
        region: Region,
    ): Either<Throwable, Unit>
}