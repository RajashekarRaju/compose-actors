package com.developersbreach.composeactors.core.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.developersbreach.composeactors.core.database.entity.FavoriteActorsEntity

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

    @Query("SELECT column_actor_id FROM favorite_actors_table WHERE column_actor_id = :actorId")
    fun checkIfActorIsFavorite(actorId: Int): LiveData<Int>
}