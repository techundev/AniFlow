package com.techun.dev.aniflow.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.techun.dev.aniflow.core.ex.back
import com.techun.dev.aniflow.core.navigation.NavRoutes.Error
import com.techun.dev.aniflow.core.navigation.NavRoutes.Favorites
import com.techun.dev.aniflow.core.navigation.NavRoutes.Home
import com.techun.dev.aniflow.core.navigation.NavRoutes.Profile
import com.techun.dev.aniflow.favorite.ui.FavoritesScreen
import com.techun.dev.aniflow.feed.ui.FeedScreen
import com.techun.dev.aniflow.profile.ProfileScreen

@Composable
fun NavigationWrapper(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = {
            backStack.back()
        },
        entryProvider = entryProvider {
            entry<Home> {
                FeedScreen()
            }
            entry<Favorites> {
                FavoritesScreen()
            }
            entry<Profile> {
                ProfileScreen()
            }
            entry<Error> { }
        })
}