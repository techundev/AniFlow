package com.techun.dev.aniflow.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data object Favorites : NavKey

@Serializable
data object Profile : NavKey