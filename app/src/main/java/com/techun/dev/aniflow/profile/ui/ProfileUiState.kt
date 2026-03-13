package com.techun.dev.aniflow.profile.ui

data class ProfileUiState(
    val userName: String = "Otaku",
    val avatarUrl: String = "",
    val totalNews: Int = 0,
    val totalFavorites: Int = 0,
    val isEditingName: Boolean = false
)