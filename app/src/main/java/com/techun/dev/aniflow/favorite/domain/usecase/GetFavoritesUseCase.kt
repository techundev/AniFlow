package com.techun.dev.aniflow.favorite.domain.usecase

import com.techun.dev.aniflow.favorite.domain.model.FavoriteItem
import com.techun.dev.aniflow.favorite.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<List<FavoriteItem>> = repository.getFavorites()
}