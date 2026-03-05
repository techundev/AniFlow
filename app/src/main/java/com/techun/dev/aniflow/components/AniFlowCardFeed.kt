package com.techun.dev.aniflow.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun AniFlowCardFeed(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = {}) {


    }
}