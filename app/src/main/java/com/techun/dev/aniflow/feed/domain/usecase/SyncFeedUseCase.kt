package com.techun.dev.aniflow.feed.domain.usecase

import com.techun.dev.aniflow.feed.domain.repository.FeedRepository

class SyncFeedUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.syncFeed()
}