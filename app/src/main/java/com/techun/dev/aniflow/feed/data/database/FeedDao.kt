package com.techun.dev.aniflow.feed.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Query("SELECT * FROM RssItem")
    fun getAllNews(): Flow<List<RssItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<RssItemEntity>)

    @Query(
        """
        UPDATE RssItem 
        SET title = :title, 
            description = :description, 
            pubDate = :pubDate, 
            imageUrl = :imageUrl
        WHERE id = :id
    """
    )
    suspend fun updateNewsContent(
        id: String,
        title: String?,
        description: String?,
        pubDate: String?,
        imageUrl: String?
    )

    @Transaction
    suspend fun syncNews(entities: List<RssItemEntity>) {
        insertNews(entities)
        entities.forEach { entity ->
            updateNewsContent(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                pubDate = entity.pubDate,
                imageUrl = entity.imageUrl
            )
        }
    }
}