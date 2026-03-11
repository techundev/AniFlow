package com.techun.dev.aniflow.detail.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity

@Dao
interface DetailsDao {
    @Query("SELECT * FROM RssItem WHERE id = :id")
    suspend fun getNewsItemById(id: String): RssItemEntity?
}