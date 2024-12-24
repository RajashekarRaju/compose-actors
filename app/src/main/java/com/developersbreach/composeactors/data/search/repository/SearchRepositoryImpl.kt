package com.developersbreach.composeactors.data.search.repository

import arrow.core.Either
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.search.remote.SearchApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {

    override suspend fun getSearchableActorsData(
        query: String
    ): Either<Throwable, List<Person>> = withContext(Dispatchers.IO) {
        searchApi.getSearchableActorsData(query).map {
            it.data
        }
    }

    override suspend fun getSearchableMoviesData(
        query: String
    ): Either<Throwable, List<Movie>> = withContext(Dispatchers.IO) {
        searchApi.getSearchableMoviesData(query).map {
            it.data
        }
    }
}