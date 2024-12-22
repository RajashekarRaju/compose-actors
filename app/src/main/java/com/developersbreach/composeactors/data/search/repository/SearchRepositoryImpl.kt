package com.developersbreach.composeactors.data.search.repository

import com.developersbreach.composeactors.data.model.Person
import com.developersbreach.composeactors.data.model.Movie
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
    ): List<Person> = withContext(Dispatchers.IO) {
        searchApi.getSearchableActorsData(query).data
    }

    override suspend fun getSearchableMoviesData(
        query: String
    ): List<Movie> = withContext(Dispatchers.IO) {
        searchApi.getSearchableMoviesData(query).data
    }
}