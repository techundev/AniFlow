package com.techun.dev.aniflow.home.domain.usecase

import com.techun.dev.aniflow.home.domain.repository.FeedRepository

class SyncFeedUseCase(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.syncFeed()
}