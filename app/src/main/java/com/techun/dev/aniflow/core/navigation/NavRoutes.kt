package com.techun.dev.aniflow.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class NavRoutes : NavKey {
    @Serializable
    data object Feed : NavRoutes()

    @Serializable
    data object Favorites : NavRoutes()

    @Serializable
    data object Profile : NavRoutes()

    @Serializable
    object Error : NavRoutes()

    @Serializable
    data class Detail(val newsItemId: String) : NavRoutes()
}


