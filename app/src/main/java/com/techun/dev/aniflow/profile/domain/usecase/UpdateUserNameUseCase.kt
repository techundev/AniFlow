package com.techun.dev.aniflow.profile.domain.usecase

import com.techun.dev.aniflow.profile.domain.repository.ProfileRepository

class UpdateUserNameUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(name: String) = repository.updateUserName(name)
}