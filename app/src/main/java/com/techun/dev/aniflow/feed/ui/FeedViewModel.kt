package com.techun.dev.aniflow.feed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.favorite.data.mapper.toFavoriteItem
import com.techun.dev.aniflow.favorite.domain.usecase.AddFavoriteUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.GetFavoriteIdsUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.RemoveFavoriteUseCase
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import com.techun.dev.aniflow.feed.domain.usecase.GetFeedUseCase
import com.techun.dev.aniflow.feed.domain.usecase.SyncFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeedUseCase: GetFeedUseCase,
    private val syncFeedUseCase: SyncFeedUseCase,
    private val getFavoriteIdsUseCase: GetFavoriteIdsUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        observeFeed()
        syncFeed()
    }

    private fun observeFeed() = viewModelScope.launch {
        combine(
            getFeedUseCase(),
            getFavoriteIdsUseCase()
        ) { items, favoriteIds ->
            items.map { item ->
                item.copy(isFavorite = item.id in favoriteIds)
            }
        }.collect { items ->
            _uiState.value = if (items.isEmpty()) {
                FeedUiState.Loading
            } else {
                FeedUiState.Success(items)
            }
        }
    }

    fun syncFeed() = viewModelScope.launch {
        syncFeedUseCase().onFailure { error ->
            if (_uiState.value is FeedUiState.Loading) {
                _uiState.value = FeedUiState.Error(
                    error.message ?: "Error desconocido"
                )
            }
        }
    }

    fun refresh() = viewModelScope.launch {
        _isRefreshing.value = true
        syncFeedUseCase()
            .onFailure { error ->
                if (_uiState.value is FeedUiState.Loading) {
                    _uiState.value = FeedUiState.Error(error.message ?: "Error desconocido")
                }
            }
        _isRefreshing.value = false
    }

    fun toggleFavorite(item: NewsItem) = viewModelScope.launch {
        if (item.isFavorite) {
            removeFavoriteUseCase(item.id)
        } else {
            addFavoriteUseCase(item.toFavoriteItem())
        }
    }
}