package com.developersbreach.composeactors.core.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developersbreach.composeactors.core.database.entity.FavoritePersonsEntity

@Dao
interface FavoritePersonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPersonToFavorites(favoritePersonsEntity: FavoritePersonsEntity)

    @Query("SELECT * FROM favorite_persons_table")
    fun getAllFavoritePersons(): LiveData<List<FavoritePersonsEntity>>

    @Query("DELETE FROM favorite_persons_table")
    fun deleteAllFavoritePersons()

    @Delete
    fun deleteSelectedFavoritePerson(favoritePersonsEntity: FavoritePersonsEntity)

    @Query("SELECT column_person_id FROM favorite_persons_table WHERE column_person_id = :personId")
    fun checkIfPersonIsFavorite(personId: Int): LiveData<Int>
}