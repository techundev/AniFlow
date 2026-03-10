package com.techun.dev.aniflow.home.domain.usecase

import com.techun.dev.aniflow.home.domain.model.NewsItem
import com.techun.dev.aniflow.home.domain.repository.FeedRepository

class GetFeedUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(): Result<List<NewsItem>> {
        return repository.getFeed()
    }
}