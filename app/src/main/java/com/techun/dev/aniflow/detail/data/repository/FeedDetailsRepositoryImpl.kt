package com.techun.dev.aniflow.detail.data.repository

import com.techun.dev.aniflow.core.data.database.entity.toNewsItem
import com.techun.dev.aniflow.detail.data.local.dao.DetailsDao
import com.techun.dev.aniflow.detail.domain.repository.FeedDetailsRepository
import com.techun.dev.aniflow.feed.domain.model.NewsItem

class FeedDetailsRepositoryImpl(private val dao: DetailsDao) : FeedDetailsRepository {
    override suspend fun getNewsItemById(id: String): NewsItem? {
        return dao.getNewsItemById(id)?.toNewsItem()
    }
}