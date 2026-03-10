package com.techun.dev.aniflow.favorite.domain

data class FavoriteItem(
    val id: String,
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val imageUrl: String,
    val savedAt: Long
)
