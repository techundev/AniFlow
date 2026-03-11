package com.techun.dev.aniflow.favorite.ui

import com.techun.dev.aniflow.favorite.domain.model.FavoriteItem

interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data object Empty : FavoritesUiState
    data class Success(val items: List<FavoriteItem>) : FavoritesUiState
}