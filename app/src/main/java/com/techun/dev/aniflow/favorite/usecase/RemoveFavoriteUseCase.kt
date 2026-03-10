package com.techun.dev.aniflow.favorite.usecase

import com.techun.dev.aniflow.favorite.repository.FavoritesRepository

class RemoveFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(id: String) = repository.removeFavorite(id)
}