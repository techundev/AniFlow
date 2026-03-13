package com.techun.dev.aniflow.core.data.model

enum class RssSource(val url: String, val label: String) {
    MAL("https://myanimelist.net/rss/news.xml", "MAL"),
    CRUNCHYROLL("https://www.crunchyroll.com/rss/anime", "CR")
}