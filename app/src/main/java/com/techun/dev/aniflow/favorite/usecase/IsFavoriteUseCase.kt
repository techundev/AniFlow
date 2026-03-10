package com.techun.dev.aniflow.favorite.usecase

import com.techun.dev.aniflow.favorite.repository.FavoritesRepository

class IsFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(id: String) = repository.isFavorite(id)
}