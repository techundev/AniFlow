package com.techun.dev.aniflow.home.domain.model

data class NewsItem(
    val title: String,       // Título del artículo
    val link: String,        // URL para abrir en navegador
    val description: String, // Resumen del artículo
    val pubDate: String,     // Fecha de publicación
    val imageUrl: String     // Imagen del artículo (MAL siempre incluye una)
)
