package com.techun.dev.aniflow.favorite.data.repository

import com.techun.dev.aniflow.favorite.data.local.dao.FavoritesDao
import com.techun.dev.aniflow.favorite.data.mapper.toEntity
import com.techun.dev.aniflow.favorite.data.mapper.toFavoriteItem
import com.techun.dev.aniflow.favorite.domain.model.FavoriteItem
import com.techun.dev.aniflow.favorite.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val dao: FavoritesDao
) : FavoritesRepository {
    override fun getFavorites(): Flow<List<FavoriteItem>> {
        return dao.getAllFavorites().map { entities ->
            entities.map { it.toFavoriteItem() }
        }
    }

    override fun getFavoriteIds(): Flow<Set<String>> {
        return dao.getFavoriteIds()
    }

    override suspend fun addFavorite(item: FavoriteItem) {
        dao.addFavorite(item.toEntity())
    }

    override suspend fun removeFavorite(id: String) {
        dao.removeFavorite(id)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return dao.isFavorite(id)
    }
}