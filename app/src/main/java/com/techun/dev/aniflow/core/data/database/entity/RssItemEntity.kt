package com.techun.dev.aniflow.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techun.dev.aniflow.feed.domain.model.NewsItem

@Entity(tableName = "RssItem")
data class RssItemEntity(
    @PrimaryKey val id: String,
    val title: String? = null,
    val link: String? = null,
    val description: String? = null,
    val pubDate: String? = null,
    val author: String? = null,
    val imageUrl: String? = null,
    val source: String = "MAL"
)

