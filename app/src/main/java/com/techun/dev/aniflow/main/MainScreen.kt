package com.techun.dev.aniflow.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import com.techun.dev.aniflow.core.utils.ex.navigateTo
import com.techun.dev.aniflow.core.navigation.NavRoutes
import com.techun.dev.aniflow.core.navigation.NavigationWrapper
import com.techun.dev.aniflow.core.navigation.bottomNavItems
import com.techun.dev.aniflow.core.utils.ex.back
import com.techun.dev.aniflow.core.utils.ex.backTo
import com.techun.dev.aniflow.main.composables.AniFlowAppBottomBar

@Composable
fun MainScreen() {
    val backStack = rememberNavBackStack(NavRoutes.Home)
    val currentRoute = backStack.last()
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AniFlowAppBottomBar(
                    currentRoute = currentRoute,
                    onItemSelected = { route ->
                        if (currentRoute != route) {
                            if (route in backStack) {
                                backStack.backTo(route)
                            } else {
                                backStack.navigateTo(route)
                            }
                        }
                    }
                )
            }

        }
    ) { innerPadding ->
        NavigationWrapper(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding)
        )
    }
}