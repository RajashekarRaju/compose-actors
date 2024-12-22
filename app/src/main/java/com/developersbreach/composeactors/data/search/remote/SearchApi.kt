package com.developersbreach.composeactors.data.search.remote

import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.model.Person
import com.developersbreach.composeactors.data.model.Movie

interface SearchApi {
    suspend fun getSearchableActorsData(query: String): PagedResponse<Person>
    suspend fun getSearchableMoviesData(query: String): PagedResponse<Movie>
}