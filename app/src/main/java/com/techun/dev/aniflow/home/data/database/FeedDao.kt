package com.techun.dev.aniflow.home.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Query("SELECT * FROM RssItem")
    fun getAllNews(): Flow<List<RssItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<RssItemEntity>)

    @Query("""
        UPDATE RssItem 
        SET title = :title, 
            description = :description, 
            pubDate = :pubDate, 
            imageUrl = :imageUrl
        WHERE id = :id
    """)
    suspend fun updateNewsContent(
        id: Int,
        title: String?,
        description: String?,
        pubDate: String?,
        imageUrl: String?
    )
}