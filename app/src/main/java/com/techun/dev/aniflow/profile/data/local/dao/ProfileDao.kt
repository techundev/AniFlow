package com.techun.dev.aniflow.profile.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT COUNT(*) FROM RssItem")
    fun getTotalNews(): Flow<Int>

    @Query("SELECT COUNT(*) FROM Favorites")
    fun getTotalFavorites(): Flow<Int>
}