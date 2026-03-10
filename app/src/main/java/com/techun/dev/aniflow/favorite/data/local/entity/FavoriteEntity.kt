package com.techun.dev.aniflow.favorite.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val imageUrl: String,
    val savedAt: Long = System.currentTimeMillis()
)
