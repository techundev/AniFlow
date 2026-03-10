package com.techun.dev.aniflow.favorite.usecase

import com.techun.dev.aniflow.favorite.domain.FavoriteItem
import com.techun.dev.aniflow.favorite.repository.FavoritesRepository

class AddFavoriteUseCase(private val repository: FavoritesRepository) {
    suspend operator fun invoke(item: FavoriteItem) = repository.addFavorite(item)
}