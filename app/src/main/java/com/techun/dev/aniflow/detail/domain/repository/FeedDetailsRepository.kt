package com.techun.dev.aniflow.detail.domain.repository

import com.techun.dev.aniflow.feed.domain.model.NewsItem

interface FeedDetailsRepository {
    suspend fun getNewsItemById(id: String): NewsItem?
}