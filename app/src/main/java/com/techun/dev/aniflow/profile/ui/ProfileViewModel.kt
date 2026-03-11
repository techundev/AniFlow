package com.techun.dev.aniflow.profile.ui

import androidx.lifecycle.ViewModel
import com.techun.dev.aniflow.profile.domain.usecase.GetProfileStatsUseCase
import com.techun.dev.aniflow.profile.domain.usecase.GetUserPreferencesUseCase
import com.techun.dev.aniflow.profile.domain.usecase.UpdateAvatarUrlUseCase
import com.techun.dev.aniflow.profile.domain.usecase.UpdateUserNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileViewModel(
    private val getProfileStatsUseCase: GetProfileStatsUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val updateAvatarUrlUseCase: UpdateAvatarUrlUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loa)
}