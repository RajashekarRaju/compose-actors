package com.developersbreach.composeactors.data.datasource.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import arrow.core.Either
import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.core.database.entity.PersonDetailEntity
import com.developersbreach.composeactors.core.database.entity.toWatchlistPersons
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.model.WatchlistPerson
import com.developersbreach.composeactors.data.person.model.toEntity
import com.developersbreach.composeactors.data.person.model.toWatchlistPersonsEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class DatabaseDataSource @Inject constructor(
    private val database: AppDatabase,
) {
    fun getAllPersonsFromWatchlist(): LiveData<List<WatchlistPerson>> {
        return database.watchlistPersonsDao.getAllPersonsFromWatchlist().map {
            it.toWatchlistPersons()
        }
    }

    fun checkIfPersonIsInWatchlist(
        personId: Int,
    ) = database.watchlistPersonsDao.checkIfPersonIsInWatchlist(personId)

    suspend fun addPersonToWatchlist(
        watchlistPerson: WatchlistPerson,
    ) = withContext(Dispatchers.IO) {
        with(watchlistPerson.toWatchlistPersonsEntity()) {
            database.watchlistPersonsDao.addPersonToWatchlist(watchlistPersonsEntity = this)
        }
    }

    suspend fun deleteSelectedPersonFromWatchlist(
        watchlistPerson: WatchlistPerson,
    ) = withContext(Dispatchers.IO) {
        with(watchlistPerson.toWatchlistPersonsEntity()) {
            database.watchlistPersonsDao.deleteSelectedPersonFromWatchlist(watchlistPersonsEntity = this)
        }
    }

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