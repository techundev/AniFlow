package com.techun.dev.aniflow.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.techun.dev.aniflow.core.navigation.Favorites
import com.techun.dev.aniflow.core.navigation.Home
import com.techun.dev.aniflow.core.navigation.Profile
import com.techun.dev.aniflow.favorite.FavoriteScreen
import com.techun.dev.aniflow.home.HomeScreen
import com.techun.dev.aniflow.main.composables.AniFlowAppBottomBar
import com.techun.dev.aniflow.profile.ProfileScreen

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val currentRoute by viewModel.currentRoute.collectAsStateWithLifecycle()
    val backStack = rememberNavBackStack(Home)

    LaunchedEffect(currentRoute) {
        if (backStack.last() != currentRoute) {
            backStack.clear()
            backStack.add(currentRoute)
        }
    }

    Scaffold(
        bottomBar = {
            AniFlowAppBottomBar(
                currentRoute = currentRoute,
                onItemSelected = { route ->
                    viewModel.navigateTo(route)
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
            entryProvider = entryProvider {
                entry<Home> { HomeScreen() }
                entry<Favorites> { FavoriteScreen() }
                entry<Profile> { ProfileScreen() }
            }
        )
    }
}