package com.techun.dev.aniflow.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.favorite.domain.usecase.GetFavoritesUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.RemoveFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoritesUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> =
        getFavoritesUseCase()
            .map { items ->
                if (items.isEmpty()) FavoritesUiState.Empty
                else FavoritesUiState.Success(items)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FavoritesUiState.Loading
            )

    fun removeFavorites(id: String) = viewModelScope.launch {
        removeFavoritesUseCase(id)
    }
}