package com.techun.dev.aniflow.feed.ui

import com.techun.dev.aniflow.feed.domain.model.NewsItem

sealed interface FeedUiState {
    data object Loading : FeedUiState

    data class Success(val items: List<NewsItem>) : FeedUiState

    data class Error(val message: String) : FeedUiState
}