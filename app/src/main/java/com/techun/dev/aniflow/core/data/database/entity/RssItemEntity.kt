package com.techun.dev.aniflow.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techun.dev.aniflow.home.domain.model.NewsItem

@Entity(tableName = "RssItem")
data class RssItemEntity(
    @PrimaryKey val id: Int,
    val title: String? = null,
    val link: String? = null,
    val description: String? = null,
    val pubDate: String? = null,
    val author: String? = null,
    val imageUrl: String? = null,
    val isFavorite: Boolean? = null
)

fun RssItemEntity.toNewsItem() = NewsItem(
    title = title.orEmpty(),
    link = link.orEmpty(),
    description = description.orEmpty(),
    pubDate = pubDate.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    isFavorite = isFavorite ?: false
)