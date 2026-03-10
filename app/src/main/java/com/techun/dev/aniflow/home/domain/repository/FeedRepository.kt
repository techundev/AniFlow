package com.techun.dev.aniflow.home.domain.repository

import com.techun.dev.aniflow.home.domain.model.NewsItem

interface FeedRepository {
    suspend fun getFeed(): Result<List<NewsItem>>
}