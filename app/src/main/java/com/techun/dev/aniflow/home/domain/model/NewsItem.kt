package com.techun.dev.aniflow.home.domain.model

data class NewsItem(
    val id: String,
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val imageUrl: String,
    val isFavorite: Boolean
)
