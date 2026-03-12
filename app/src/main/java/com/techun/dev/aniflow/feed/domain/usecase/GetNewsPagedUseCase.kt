package com.techun.dev.aniflow.feed.domain.usecase

import com.techun.dev.aniflow.feed.domain.model.NewsItem
import com.techun.dev.aniflow.feed.domain.repository.FeedRepository

class GetNewsPagedUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(limit: Int, offset: Int): List<NewsItem> {
        return repository.getNewsPaged(limit, offset)
    }
}