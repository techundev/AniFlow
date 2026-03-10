package com.techun.dev.aniflow.home.data.model

import com.techun.dev.aniflow.home.domain.model.NewsItem

data class RssItemDto(
    val title: String,       // Título del artículo
    val link: String,        // URL del artículo
    val description: String, // Resumen/descripción
    val pubDate: String,     // Fecha de publicación
    val author: String,       // Autor (puede estar vacío en HN)
    val imageUrl: String
)

fun RssItemDto.toNewsItem() = NewsItem(
    title = title,
    link = link,
    description = description,
    pubDate = pubDate,
    imageUrl = imageUrl
)