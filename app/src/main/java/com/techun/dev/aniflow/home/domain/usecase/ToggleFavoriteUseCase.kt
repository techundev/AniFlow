package com.techun.dev.aniflow.home.domain.usecase

import com.techun.dev.aniflow.home.domain.repository.FeedRepository

class ToggleFavoriteUseCase(private val repository: FeedRepository) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) {
//        repository.toggleFavorite(id, isFavorite)
    }
}