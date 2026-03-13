package com.techun.dev.aniflow.feed.domain.repository

import com.techun.dev.aniflow.feed.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeed(): Flow<List<NewsItem>>

    suspend fun syncFeed(): Result<Unit>
    suspend fun getNewsPaged(limit: Int, offset: Int):List<NewsItem>
}