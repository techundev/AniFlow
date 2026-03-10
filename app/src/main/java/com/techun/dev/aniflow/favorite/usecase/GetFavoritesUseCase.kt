package com.techun.dev.aniflow.favorite.usecase

import com.techun.dev.aniflow.favorite.domain.FavoriteItem
import com.techun.dev.aniflow.favorite.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<List<FavoriteItem>> = repository.getFavorites()
}