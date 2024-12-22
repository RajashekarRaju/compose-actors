package com.developersbreach.composeactors.data.search.repository

import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.Movie

interface SearchRepository {
    suspend fun getSearchableActorsData(query: String): List<Actor>
    suspend fun getSearchableMoviesData(query: String): List<Movie>
}