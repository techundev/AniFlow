package com.techun.dev.aniflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.techun.dev.aniflow.main.MainScreen
import com.techun.dev.aniflow.ui.theme.AniFlowTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AniFlowTheme {
                MainScreen()
            }
        }
    }
}

