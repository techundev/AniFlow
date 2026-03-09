package com.techun.dev.aniflow.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class NavRoutes : NavKey {
    @Serializable
    data object Home : NavRoutes()

    @Serializable
    data object Favorites : NavRoutes()

    @Serializable
    data object Profile : NavRoutes()

    @Serializable
    object Error : NavRoutes()
}


