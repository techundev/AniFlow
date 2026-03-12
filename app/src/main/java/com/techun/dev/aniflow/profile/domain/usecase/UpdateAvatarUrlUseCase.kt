package com.techun.dev.aniflow.profile.domain.usecase

import com.techun.dev.aniflow.profile.domain.repository.ProfileRepository

class UpdateAvatarUrlUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(url: String) = repository.updateAvatarUrl(url)
}