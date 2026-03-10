package com.techun.dev.aniflow.home.domain.repository

import com.techun.dev.aniflow.home.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getFeed(): Flow<List<NewsItem>>

    suspend fun syncFeed(): Result<Unit>
//    suspend fun toggleFavorite(id: Int, isFavorite: Boolean)
}