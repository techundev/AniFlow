package com.techun.dev.aniflow.home.data.repository

import com.techun.dev.aniflow.home.data.model.toNewsItem
import com.techun.dev.aniflow.home.data.remote.FeedRemoteDataSource
import com.techun.dev.aniflow.home.domain.model.NewsItem
import com.techun.dev.aniflow.home.domain.repository.FeedRepository

class FeedRepositoryImpl(private val remoteDataSource: FeedRemoteDataSource) : FeedRepository {
    override suspend fun getFeed(): Result<List<NewsItem>> {
        return try {
            val items = remoteDataSource.fetchFeed()
            Result.success(items.map { it.toNewsItem() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}