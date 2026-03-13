package com.techun.dev.aniflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.techun.dev.aniflow.main.MainScreen
import com.techun.dev.aniflow.ui.theme.AniFlowTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        splashScreen.setKeepOnScreenCondition {
            !isReady
        }

        lifecycleScope.launch {
            delay(2000)
            isReady = true
        }

        setContent {
            AniFlowTheme {
                MainScreen()
            }
        }
    }
}

