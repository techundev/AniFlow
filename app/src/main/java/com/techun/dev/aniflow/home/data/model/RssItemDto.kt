package com.techun.dev.aniflow.home.data.model

import com.techun.dev.aniflow.home.domain.model.NewsItem

data class RssItemDto(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val author: String,
    val imageUrl: String
)

fun RssItemDto.toNewsItem() = NewsItem(
    title = title, link = link, description = description, pubDate = pubDate, imageUrl = imageUrl
)