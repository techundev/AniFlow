package com.techun.dev.aniflow.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.home.domain.usecase.GetFeedUseCase
import com.techun.dev.aniflow.home.domain.usecase.SyncFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeedUseCase: GetFeedUseCase,
    private val syncFeedUseCase: SyncFeedUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        observeFeed()
        syncFeed()
    }

    private fun observeFeed() {
        viewModelScope.launch {
            getFeedUseCase().collect { items ->
                _uiState.value = if (items.isEmpty()) {
                    FeedUiState.Loading
                } else {
                    FeedUiState.Success(items)
                }
            }
        }
    }

    fun syncFeed() {
        viewModelScope.launch {
            syncFeedUseCase()
                .onFailure { error ->
                    if (_uiState.value is FeedUiState.Loading) {
                        _uiState.value = FeedUiState.Error(
                            error.message ?: "Error desconocido"
                        )
                    }
                }
        }
    }
}