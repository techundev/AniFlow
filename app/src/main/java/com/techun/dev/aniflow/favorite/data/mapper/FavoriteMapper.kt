package com.techun.dev.aniflow.favorite.data.mapper

import com.techun.dev.aniflow.favorite.data.local.entity.FavoriteEntity
import com.techun.dev.aniflow.favorite.domain.model.FavoriteItem
import com.techun.dev.aniflow.home.domain.model.NewsItem

// Entity → dominio
fun FavoriteEntity.toFavoriteItem() = FavoriteItem(
    id = id,
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    imageUrl = imageUrl,
    savedAt = savedAt
)

// Dominio → Entity
fun FavoriteItem.toEntity() = FavoriteEntity(
    id = id,
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    imageUrl = imageUrl,
    savedAt = savedAt
)

// NewsItem → FavoriteItem
fun NewsItem.toFavoriteItem() = FavoriteItem(
    id = id,
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    imageUrl = imageUrl,
    savedAt = System.currentTimeMillis()
)