package com.techun.dev.aniflow.profile.domain.usecase

import com.techun.dev.aniflow.profile.domain.model.UserPreferences
import com.techun.dev.aniflow.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetUserPreferencesUseCase(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<UserPreferences> = repository.getUserPreferences()
}