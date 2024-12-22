package com.developersbreach.composeactors.data.person.repository

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.model.FavoritePerson
import com.developersbreach.composeactors.data.movie.model.Movie

interface PersonRepository {
    suspend fun getPopularPersons(): List<Person>
    suspend fun getTrendingPersons(): List<Person>
    suspend fun getPersonDetails(personId: Int): PersonDetail
    suspend fun getCastDetails(personId: Int): List<Movie>
    fun isFavoritePerson(personId: Int): LiveData<Int>
    suspend fun addPersonToFavorite(favoritePerson: FavoritePerson)
    suspend fun deleteSelectedFavoritePerson(favoritePerson: FavoritePerson)
    fun getAllFavoritePersons(): LiveData<List<FavoritePerson>>
}