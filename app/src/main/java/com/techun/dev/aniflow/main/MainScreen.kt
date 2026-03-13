package com.techun.dev.aniflow.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.techun.dev.aniflow.core.navigation.NavRoutes
import com.techun.dev.aniflow.core.navigation.NavigationWrapper
import com.techun.dev.aniflow.core.navigation.bottomNavItems
import com.techun.dev.aniflow.core.utils.ex.backTo
import com.techun.dev.aniflow.core.utils.ex.navigateTo
import com.techun.dev.aniflow.main.composables.AniFlowAppBottomBar

@Composable
fun MainScreen() {
    val backStack = rememberNavBackStack(NavRoutes.Feed)
    val currentRoute = backStack.last()
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar, enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300, easing = EaseInOut)
                ), exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300, easing = EaseInOut)
                )
            ) {
                AniFlowAppBottomBar(
                    currentRoute = currentRoute, onItemSelected = { route ->
                        if (currentRoute != route) {
                            if (route in backStack) {
                                backStack.backTo(route)
                            } else {
                                backStack.navigateTo(route)
                            }
                        }
                    })
            }
        }) { innerPadding ->
        NavigationWrapper(
            backStack = backStack, modifier = Modifier.padding(innerPadding)
        )
    }
}