package com.techun.dev.aniflow.feed.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.favorite.data.mapper.toFavoriteItem
import com.techun.dev.aniflow.favorite.domain.usecase.AddFavoriteUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.GetFavoriteIdsUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.RemoveFavoriteUseCase
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import com.techun.dev.aniflow.feed.domain.usecase.GetFeedUseCase
import com.techun.dev.aniflow.feed.domain.usecase.GetNewsPagedUseCase
import com.techun.dev.aniflow.feed.domain.usecase.SyncFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeedUseCase: GetFeedUseCase,
    private val syncFeedUseCase: SyncFeedUseCase,
    private val getFavoriteIdsUseCase: GetFavoriteIdsUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val getNewsPagedUseCase: GetNewsPagedUseCase
) : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 10
    }

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private val _hasMoreItems = MutableStateFlow(true)
    val hasMoreItems: StateFlow<Boolean> = _hasMoreItems.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _pagedItems = MutableStateFlow<List<NewsItem>>(emptyList())

    private var currentOffset = 0

    init {
        observeFavorites()
        syncFeed()
        loadNextPage()
    }

    fun loadNextPage() {
        if (_isLoadingMore.value || !_hasMoreItems.value) return

        viewModelScope.launch {
            _isLoadingMore.value = true

            val favoriteIds = getFavoriteIdsUseCase().first()
            val newItems = getNewsPagedUseCase(
                limit = PAGE_SIZE + 1,
                offset = currentOffset
            ).map { item -> item.copy(isFavorite = item.id in favoriteIds) }

            if (newItems.isEmpty()) {
                _hasMoreItems.value = false
            } else {
                val hasMore = newItems.size > PAGE_SIZE
                val itemsToAdd = if (hasMore) newItems.take(PAGE_SIZE) else newItems

                _pagedItems.value += itemsToAdd
                currentOffset += itemsToAdd.size
                _hasMoreItems.value = hasMore
                _uiState.value = FeedUiState.Success(_pagedItems.value)
            }
            _isLoadingMore.value = false
        }
    }

    private fun observeFavorites() = viewModelScope.launch {
        getFavoriteIdsUseCase().collect { favoriteIds ->
            val current = _pagedItems.value
            if (current.isNotEmpty()){
                _pagedItems.value = current.map {item ->
                    item.copy(isFavorite = item.id in favoriteIds)
                }
            }

            val updated =_pagedItems.value
            if (updated.isNotEmpty()){
                _uiState.value = FeedUiState.Success(updated)
            }
        }
    }

    fun syncFeed() = viewModelScope.launch {
        syncFeedUseCase()
            .onSuccess {
                resetPagination()
            }
            .onFailure { error ->
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
            .onSuccess { resetPagination() }
            .onFailure { error ->
                if (_uiState.value is FeedUiState.Loading) {
                    _uiState.value = FeedUiState.Error(
                        error.message ?: "Error desconocido"
                    )
                }
            }
        _isRefreshing.value = false
    }

    fun resetPagination() {
        currentOffset = 0
        _hasMoreItems.value = true
        _pagedItems.value = emptyList()
        _isLoadingMore.value = false
        loadNextPage()
    }

    fun toggleFavorite(item: NewsItem) = viewModelScope.launch {
        if (item.isFavorite) {
            removeFavoriteUseCase(item.id)
        } else {
            addFavoriteUseCase(item.toFavoriteItem())
        }
    }
}