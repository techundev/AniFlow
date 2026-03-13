package com.techun.dev.aniflow.detail.ui

import com.techun.dev.aniflow.feed.domain.model.NewsItem

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val newsItem: NewsItem) : DetailUiState
    data class Error(val message: String) : DetailUiState
}