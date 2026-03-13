package com.techun.dev.aniflow.favorite.domain.usecase

import com.techun.dev.aniflow.favorite.domain.model.FavoriteItem
import com.techun.dev.aniflow.favorite.domain.repository.FavoritesRepository

class AddFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(item: FavoriteItem) = repository.addFavorite(item)
}