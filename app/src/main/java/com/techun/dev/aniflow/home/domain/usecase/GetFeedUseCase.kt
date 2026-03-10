package com.techun.dev.aniflow.home.domain.usecase

import com.techun.dev.aniflow.home.domain.model.NewsItem
import com.techun.dev.aniflow.home.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetFeedUseCase(private val repository: FeedRepository) {
    operator fun invoke(): Flow<List<NewsItem>> = repository.getFeed()
}