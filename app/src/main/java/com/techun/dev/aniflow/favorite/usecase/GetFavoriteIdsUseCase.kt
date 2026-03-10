package com.techun.dev.aniflow.favorite.usecase

import com.techun.dev.aniflow.favorite.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteIdsUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<Set<String>> = repository.getFavoriteIds()
}