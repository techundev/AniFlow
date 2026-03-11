package com.techun.dev.aniflow.feed.data.repository

import com.techun.dev.aniflow.core.data.database.entity.toNewsItem
import com.techun.dev.aniflow.feed.data.database.FeedDao
import com.techun.dev.aniflow.feed.data.model.toEntity
import com.techun.dev.aniflow.feed.data.remote.FeedRemoteDataSource
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import com.techun.dev.aniflow.feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeedRepositoryImpl(
    private val remoteDataSource: FeedRemoteDataSource, private val dao: FeedDao
) : FeedRepository {
    override fun getFeed(): Flow<List<NewsItem>> {
        return dao.getAllNews().map { entities -> entities.map { it.toNewsItem() } }
    }

    override suspend fun syncFeed(): Result<Unit> {
        return try {
            val items = remoteDataSource.fetchFeed()
            val entities = items.map { it.toEntity() }
            dao.syncNews(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}