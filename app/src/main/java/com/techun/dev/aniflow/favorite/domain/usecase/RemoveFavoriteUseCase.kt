package com.techun.dev.aniflow.favorite.domain.usecase

import com.techun.dev.aniflow.favorite.domain.repository.FavoritesRepository

class RemoveFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(id: String) = repository.removeFavorite(id)
}