package com.techun.dev.aniflow.feed.data.mapper

import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity
import com.techun.dev.aniflow.feed.data.model.RssItemDto

fun RssItemDto.toEntity() = RssItemEntity(
    id = link,
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    author = author,
    imageUrl = imageUrl,
    source = source
)
