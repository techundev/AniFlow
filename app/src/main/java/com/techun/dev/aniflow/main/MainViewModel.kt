package com.techun.dev.aniflow.main

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import com.techun.dev.aniflow.core.navigation.Home
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _currentRoute = MutableStateFlow<NavKey>(Home)
    val currentRoute: StateFlow<NavKey> = _currentRoute.asStateFlow()

    fun navigateTo(route: NavKey) {
        _currentRoute.value = route
    }
}