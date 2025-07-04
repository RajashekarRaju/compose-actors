package com.developersbreach.composeactors.data.person.repository

import arrow.core.Either
import com.developersbreach.composeactors.core.cache.CacheKey
import com.developersbreach.composeactors.core.cache.CacheManager
import com.developersbreach.composeactors.core.database.entity.toPersonDetail
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.remote.PersonApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class PersonRepositoryImpl @Inject constructor(
    private val personApi: PersonApi,
    private val databaseDataSource: DatabaseDataSource,
    private val cacheManager: CacheManager,
) : PersonRepository {

    override suspend fun getPopularPersons(): Either<Throwable, List<Person>> = withContext(Dispatchers.IO) {
        personApi.getPopularPersons().map {
            it.data
        }
    }

    override suspend fun getTrendingPersons(): Either<Throwable, List<Person>> = withContext(Dispatchers.IO) {
        personApi.getTrendingPersons().map {
            it.data
        }
    }

    override suspend fun getPersonDetails(
        personId: Int,
    ): Either<Throwable, PersonDetail> = withContext(Dispatchers.IO) {
        val cacheKey = CacheKey.forPerson(personId)
        val cachedData = cacheManager.get<PersonDetail>(cacheKey)
        if (cachedData != null) {
            return@withContext Either.Right(cachedData)
        }

        val dbData = databaseDataSource.getPersonDetail(personId)
        dbData.map {
            if (it != null) {
                cacheManager.put(cacheKey, it.toPersonDetail())
                return@withContext Either.Right(it.toPersonDetail())
            }
        }

        personApi.getPersonDetails(personId).map {
            cacheManager.put(cacheKey, it)
            databaseDataSource.addPersonDetail(it)
            it
        }
    }

    override suspend fun getCastDetails(
        personId: Int,
    ): Either<Throwable, List<Movie>> = withContext(Dispatchers.IO) {
        personApi.getCastDetails(personId).map {
            it.movies
        }
    }
}