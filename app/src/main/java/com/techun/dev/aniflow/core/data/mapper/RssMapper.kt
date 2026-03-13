package com.techun.dev.aniflow.core.data.mapper

import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity
import com.techun.dev.aniflow.feed.domain.model.NewsItem

fun RssItemEntity.toNewsItem() = NewsItem(
    id = id,
    title = title.orEmpty(),
    link = link.orEmpty(),
    description = description.orEmpty(),
    pubDate = pubDate.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    source = source,
    isFavorite = false
)