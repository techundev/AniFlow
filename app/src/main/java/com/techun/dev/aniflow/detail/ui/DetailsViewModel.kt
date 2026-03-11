package com.techun.dev.aniflow.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.detail.domain.usecase.GetNewsItemByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getNewsItemByIdUseCase: GetNewsItemByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadNewsItem(id: String) = viewModelScope.launch {
        getNewsItemByIdUseCase(id)
            ?.let { _uiState.value = DetailUiState.Success(it) }
            ?:run { _uiState.value= DetailUiState.Error("Artículo no encontrado") }
    }

}