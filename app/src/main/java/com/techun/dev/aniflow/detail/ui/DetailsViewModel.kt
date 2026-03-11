package com.techun.dev.aniflow.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.detail.domain.usecase.GetNewsItemByIdUseCase
import com.techun.dev.aniflow.favorite.data.mapper.toFavoriteItem
import com.techun.dev.aniflow.favorite.domain.usecase.AddFavoriteUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.GetFavoriteIdsUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.RemoveFavoriteUseCase
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import com.techun.dev.aniflow.feed.domain.usecase.GetFeedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getNewsItemByIdUseCase: GetNewsItemByIdUseCase,
    private val getFeedUseCase: GetFeedUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val getFavoriteIdsUseCase: GetFavoriteIdsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _related = MutableStateFlow<List<NewsItem>>(emptyList())
    var related: StateFlow<List<NewsItem>> = _related.asStateFlow()

    fun loadNewsItem(id: String) = viewModelScope.launch {
        combine(
            flow { emit(getNewsItemByIdUseCase(id)) }, getFavoriteIdsUseCase()
        ) { newsItem, favoriteIds ->
            newsItem?.copy(isFavorite = newsItem.id in favoriteIds)
        }.collect { newsItem ->
            if (newsItem != null) {
                _uiState.value = DetailUiState.Success(newsItem)
                loadRelated(newsItem)
            } else {
                _uiState.value = DetailUiState.Error("Artículo no encontrado")
            }
        }
    }

    private fun loadRelated(newsItem: NewsItem) = viewModelScope.launch {
        getFeedUseCase().collect { allItems ->
            val keywords = newsItem.title
                .split(" ")
                .filter { it.length > 3 }
                .take(2)

            _related.value = allItems
                .filter { item ->
                    item.id != newsItem.id &&
                            keywords.any { keyword ->
                                item.title.contains(keyword, ignoreCase = true)
                            }
                }
                .take(3)
        }
    }

    fun toggleFavorite(newsItem: NewsItem) = viewModelScope.launch {
        if (newsItem.isFavorite){
            removeFavoriteUseCase(newsItem.id)
        }else{
            addFavoriteUseCase(newsItem.toFavoriteItem())
        }
    }

}