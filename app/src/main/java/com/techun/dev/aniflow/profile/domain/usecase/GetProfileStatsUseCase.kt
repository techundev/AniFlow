package com.techun.dev.aniflow.profile.domain.usecase

import com.techun.dev.aniflow.profile.domain.model.ProfileStats
import com.techun.dev.aniflow.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileStatsUseCase(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<ProfileStats> = repository.getProfileStats()
}