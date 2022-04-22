package com.developersbreach.composeactors.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developersbreach.composeactors.repository.database.entity.FavoriteActorsEntity

@Dao
interface FavoriteActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addActorToFavorites(favoriteActorsEntity: FavoriteActorsEntity)

    @Query("SELECT * FROM favorite_actors_table")
    fun getAllFavoriteActors(): LiveData<List<FavoriteActorsEntity>>

    @Query("DELETE FROM favorite_actors_table")
    fun deleteAllFavoriteActors()

    @Delete
    fun deleteSelectedFavoriteActor(favoriteActorsEntity: FavoriteActorsEntity)
}