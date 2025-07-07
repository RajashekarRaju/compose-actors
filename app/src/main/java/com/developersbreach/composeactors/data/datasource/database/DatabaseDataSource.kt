package com.developersbreach.composeactors.data.datasource.database

import arrow.core.Either
import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.model.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseDataSource @Inject constructor(
    private val database: AppDatabase,
) {
    suspend fun addPersonDetail(
        personDetail: PersonDetail,
    ) {
        database.personDetailsDao.insertPersonDetail(personDetail.toEntity())
    }

    suspend fun getPersonDetail(
        personId: Int,
    ): Either<Throwable, PersonDetailEntity?> {
        return Either.catch {
            database.personDetailsDao.getPersonDetail(personId)
        }
    }
}