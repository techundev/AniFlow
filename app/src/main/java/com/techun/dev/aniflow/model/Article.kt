package com.techun.dev.aniflow.model

data class Article(
    val id: Int,
    val title: String,
    val summary: String,
    val content: String,
    val pubDate: String,
    val timeAgo: String,
    val imageUrl: String,
    val tag: String,
    val source: String,
    val link: String,
    val isFavorite: Boolean = false
)
