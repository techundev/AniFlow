package com.techun.dev.aniflow.favorite.domain.repository

import com.techun.dev.aniflow.favorite.domain.model.FavoriteItem
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<FavoriteItem>>
    fun getFavoriteIds(): Flow<Set<String>>
    suspend fun addFavorite(item: FavoriteItem)
    suspend fun removeFavorite(id: String)
    suspend fun isFavorite(id: String): Boolean
}