package com.techun.dev.aniflow.detail.domain.usecase

import com.techun.dev.aniflow.detail.domain.repository.FeedDetailsRepository
import com.techun.dev.aniflow.feed.domain.model.NewsItem

class GetNewsItemByIdUseCase(private val repository: FeedDetailsRepository) {
    suspend operator fun invoke(id: String): NewsItem? = repository.getNewsItemById(id)
}