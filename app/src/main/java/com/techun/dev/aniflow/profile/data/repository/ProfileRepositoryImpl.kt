package com.techun.dev.aniflow.profile.data.repository

import com.techun.dev.aniflow.profile.data.datastore.UserPreferencesDataStore
import com.techun.dev.aniflow.profile.data.local.dao.ProfileDao
import com.techun.dev.aniflow.profile.domain.model.ProfileStats
import com.techun.dev.aniflow.profile.domain.model.UserPreferences
import com.techun.dev.aniflow.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ProfileRepositoryImpl(
    private val dataStore: UserPreferencesDataStore, private val dao: ProfileDao
) : ProfileRepository {
    override fun getUserPreferences(): Flow<UserPreferences> {
        return dataStore.userPreferences
    }

    override suspend fun updateUserName(name: String) {
        dataStore.updateUserName(name)
    }

    override suspend fun updateAvatarUrl(url: String) {
        dataStore.updateAvatarUrl(url)
    }

    override fun getProfileStats(): Flow<ProfileStats> {
        return combine(
            dao.getTotalNews(),
            dao.getTotalFavorites()
        ) { totalNews, totalFavorites ->
            ProfileStats(
                totalNews = totalNews,
                totalFavorites = totalFavorites
            )
        }
    }
}