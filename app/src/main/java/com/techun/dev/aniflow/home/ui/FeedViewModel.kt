package com.techun.dev.aniflow.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.home.domain.usecase.GetFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeedUseCase: GetFeedUseCase
) : ViewModel() {
    private val TAG = "RSS_DEBUG"

    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        loadFeed()
    }

    fun loadFeed() {
        viewModelScope.launch {
            _uiState.value = FeedUiState.Loading
            Log.d(TAG, "⏳ Cargando feed...")

            // Ejecutamos el caso de uso y manejamos el resultado
            getFeedUseCase()
                .onSuccess { items ->
                    Log.d(TAG, "✅ Feed cargado: ${items.size} items")
                    items.forEachIndexed { index, item ->
                        Log.d(
                            TAG, """
                            📰 Item #$index
                            ├─ title      : ${item.title}
                            ├─ link       : ${item.link}
                            ├─ description: ${item.description.take(80)}...
                            ├─ pubDate    : ${item.pubDate}
                            └─ imageUrl   : ${item.imageUrl}
                        """.trimIndent()
                        )
                    }
                    _uiState.value = FeedUiState.Success(items)
                }
                .onFailure { error ->
                    Log.e(TAG, "❌ Error al cargar el feed: ${error.message}", error)
                    _uiState.value = FeedUiState.Error(
                        message = error.message ?: "Error desconocido"
                    )
                }
        }
    }
}