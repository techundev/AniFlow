package com.techun.dev.aniflow.home.data.model

import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity
import com.techun.dev.aniflow.home.domain.model.NewsItem

data class RssItemDto(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val author: String,
    val imageUrl: String
)


fun RssItemDto.toEntity() = RssItemEntity(
    id = link.hashCode(),
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    author = author,
    imageUrl = imageUrl,
    isFavorite = false
)

fun RssItemDto.toNewsItem() = NewsItem(
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    imageUrl = imageUrl,
    isFavorite = false
)