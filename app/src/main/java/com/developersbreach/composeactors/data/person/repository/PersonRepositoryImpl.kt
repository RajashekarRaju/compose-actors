package com.developersbreach.composeactors.data.person.repository

import androidx.lifecycle.LiveData
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.person.model.FavoritePerson
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.person.remote.PersonApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class PersonRepositoryImpl @Inject constructor(
    private val personApi: PersonApi,
    private val databaseDataSource: DatabaseDataSource
) : PersonRepository {

    override suspend fun getPopularPersons(): List<Person> = withContext(Dispatchers.IO) {
        personApi.getPopularPersons().data
    }

    override suspend fun getTrendingPersons(): List<Person> = withContext(Dispatchers.IO) {
        personApi.getTrendingPersons().data
    }

    override suspend fun getPersonDetails(personId: Int): PersonDetail = withContext(Dispatchers.IO) {
        personApi.getPersonDetails(personId)
    }

    override suspend fun getCastDetails(personId: Int): List<Movie> = withContext(Dispatchers.IO) {
        personApi.getCastDetails(personId).movies
    }

    override fun isFavoritePerson(personId: Int): LiveData<Int> {
        return databaseDataSource.checkIfPersonIsFavorite(personId)
    }

    override suspend fun addPersonToFavorite(favoritePerson: FavoritePerson) {
        databaseDataSource.addPersonToFavorites(favoritePerson)
    }

    override suspend fun deleteSelectedFavoritePerson(favoritePerson: FavoritePerson) {
        databaseDataSource.deleteSelectedFavoritePerson(favoritePerson)
    }

    override fun getAllFavoritePersons(): LiveData<List<FavoritePerson>> {
        return databaseDataSource.getAllFavoritePersons()
    }
}