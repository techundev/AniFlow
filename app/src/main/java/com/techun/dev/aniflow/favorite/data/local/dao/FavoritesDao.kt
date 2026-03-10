package com.techun.dev.aniflow.favorite.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techun.dev.aniflow.favorite.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM Favorites ORDER BY savedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT id FROM Favorites")
    fun getFavoriteIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM Favorites WHERE id = :id")
    suspend fun removeFavorite(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM Favorites WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}