package com.techun.dev.aniflow.home.ui

import com.techun.dev.aniflow.home.domain.model.NewsItem

sealed interface FeedUiState {
    // Estado inicial, cargando datos
    data object Loading : FeedUiState

    // Datos cargados exitosamente
    data class Success(val items: List<NewsItem>) : FeedUiState

    // Algo salió mal
    data class Error(val message: String) : FeedUiState
}