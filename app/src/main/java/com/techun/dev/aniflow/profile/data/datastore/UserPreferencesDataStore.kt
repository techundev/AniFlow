package com.techun.dev.aniflow.profile.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.techun.dev.aniflow.profile.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user_preferences"
    )

    private object Keys {
        val USER_NAME = stringPreferencesKey("user_name")
        val AVATAR_URL = stringPreferencesKey("avatar_url")
    }

    val userPreferences: Flow<UserPreferences> = context.dataStore.data.catch { exception ->
        if (exception is IOException) emit(emptyPreferences())
        else throw exception
    }.map { preferences ->
        UserPreferences(
            userName = preferences[Keys.USER_NAME] ?: "Otaku",
            avatarUrl = preferences[Keys.AVATAR_URL] ?: ""
        )
    }

    suspend fun updateUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.USER_NAME] = name
        }
    }

    suspend fun updateAvatarUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.AVATAR_URL] = url
        }
    }
}