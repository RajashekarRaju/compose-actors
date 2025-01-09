package com.developersbreach.composeactors.core.cache

object CacheKey {
    fun forPerson(id: Int): String = "${PERSON}_$id"
    fun forMovie(id: String): String = "${MOVIE}_$id"

    private const val PERSON = "person_"
    private const val MOVIE = "movie_"
}