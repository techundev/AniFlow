package com.techun.dev.aniflow.feed.data.model

data class RssItemDto(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val author: String,
    val imageUrl: String,
    val source: String = "MAL"
)