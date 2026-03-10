package com.techun.dev.aniflow.favorite.domain.usecase

import com.techun.dev.aniflow.favorite.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteIdsUseCase(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<Set<String>> = repository.getFavoriteIds()
}