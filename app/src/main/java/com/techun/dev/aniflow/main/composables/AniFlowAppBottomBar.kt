package com.techun.dev.aniflow.main.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.techun.dev.aniflow.components.AniFlowText
import com.techun.dev.aniflow.core.navigation.bottomNavItems

@Composable
fun AniFlowAppBottomBar(
    currentRoute: NavKey,
    onItemSelected: (NavKey) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { AniFlowText(text = item.title) }
            )
        }
    }
}