package com.developersbreach.composeactors.data.region.repository

import arrow.core.Either
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.region.model.Region
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionRepositoryImpl @Inject constructor(
    private val databaseDataSource: DatabaseDataSource,
) : RegionRepository {

    override fun getRegions(): Either<Throwable, List<Region>> {
        return Either.catch {
            Locale.getISOCountries()
                .map { code ->
                    val name = Locale("", code).getDisplayCountry(Locale.getDefault())
                    Region(code, name)
                }.sortedBy {
                    it.name
                }
        }
    }

    override suspend fun getRegion(): Either<Throwable, Region> {
        return Either.catch {
            databaseDataSource.getRegion()
        }
    }

    override suspend fun updateRegion(
        region: Region,
    ): Either<Throwable, Unit> {
        return Either.catch {
            databaseDataSource.setRegion(region)
        }
    }
}