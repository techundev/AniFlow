package com.techun.dev.aniflow.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techun.dev.aniflow.R

@Composable
fun AniFlowCardFooter(
    modifier: Modifier, source: String = "MyAnimeList", timeAgo: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AniFlowAsyncImage(
                data = when (source) {
                    "MAL" -> R.drawable.myanimelist_logo
                    "CR" -> R.drawable.crunchyroll_logo
                    else -> R.drawable.logo_splash_screen
                }, modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary)
            )
            AniFlowText(
                text = when (source) {
                    "MAL" -> "MyAnimeList"
                    "CR" -> "CrunchyRoll"
                    else -> "Default"
                }, style = MaterialTheme.typography.labelSmall, fontSize = 10.sp
            )
        }
        AniFlowText(
            text = timeAgo, fontSize = 10.sp
        )
    }
}