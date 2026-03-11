package com.techun.dev.aniflow.feed.data.model

import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity

data class RssItemDto(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val author: String,
    val imageUrl: String
)


fun RssItemDto.toEntity() = RssItemEntity(
    id = link,
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    author = author,
    imageUrl = imageUrl,
)
