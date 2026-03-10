package com.techun.dev.aniflow.favorite.repository

import com.techun.dev.aniflow.favorite.domain.FavoriteItem
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavorites(): Flow<List<FavoriteItem>>
    fun getFavoriteIds(): Flow<Set<String>>
    suspend fun addFavorite(item: FavoriteItem)
    suspend fun removeFavorite(id: String)
    suspend fun isFavorite(id: String): Boolean
}