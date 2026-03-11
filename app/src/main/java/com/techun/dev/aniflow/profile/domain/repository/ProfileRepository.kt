package com.techun.dev.aniflow.profile.domain.repository

import com.techun.dev.aniflow.profile.domain.model.ProfileStats
import com.techun.dev.aniflow.profile.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getUserPreferences(): Flow<UserPreferences>
    suspend fun updateUserName(name:String)
    suspend fun updateAvatarUrl(url: String)
    fun getProfileStats(): Flow<ProfileStats>
}