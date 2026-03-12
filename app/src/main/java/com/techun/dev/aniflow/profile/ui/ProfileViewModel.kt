package com.techun.dev.aniflow.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techun.dev.aniflow.profile.domain.usecase.GetProfileStatsUseCase
import com.techun.dev.aniflow.profile.domain.usecase.GetUserPreferencesUseCase
import com.techun.dev.aniflow.profile.domain.usecase.UpdateUserNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileStatsUseCase: GetProfileStatsUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        observePreferences()
        observeStats()
    }

    private fun observePreferences() = viewModelScope.launch {
        getUserPreferencesUseCase().collect { preferences ->
            _uiState.update { currentState ->
                currentState.copy(
                    userName = preferences.userName, avatarUrl = preferences.avatarUrl
                )
            }
        }
    }

    private fun observeStats() = viewModelScope.launch {
        getProfileStatsUseCase().collect { stats ->
            _uiState.update { currentState ->
                currentState.copy(
                    totalNews = stats.totalNews, totalFavorites = stats.totalFavorites
                )
            }
        }
    }

    fun showEditName() {
        _uiState.update { it.copy(isEditingName = true) }
    }

    fun hideEditName() {
        _uiState.update { it.copy(isEditingName = false) }
    }

    fun updateUserName(name: String) = viewModelScope.launch {
        if (name.isNotBlank()) {
            updateUserNameUseCase(name.trim())
        }
        hideEditName()
    }

}