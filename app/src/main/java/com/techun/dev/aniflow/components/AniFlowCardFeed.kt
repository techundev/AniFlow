package com.techun.dev.aniflow.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun AniFlowCardFeed(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large, onClick = {}) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(
                start = 16.dp, end = 16.dp,
                top = 14.dp, bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AniFlowText(
                text = "Tsue to Tsurugi no Wistoria Season 2 Reveals Main Promo & Theme Songs",
                fontWeight = FontWeight.Bold,
                maxLines = 3
            )
            AniFlowText(
                text = "The official website for the second season of Tsue to Tsurugi no Wistoria (Wistoria: Wand and Sword) revealed additional staff, theme songs, main visual, and the main promotional video on Thursday.",
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Spacer(modifier = Modifier.height(2.dp))
            CardFooter(
                modifier = Modifier
                    .fillMaxWidth()
            )

        }
    }
}


private val AccentPink = Color(0xFFE879F9)
private val AccentBlue = Color(0xFF38BDF8)

@Composable
private fun CardFooter(
    modifier: Modifier
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
                data = "", modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(AccentPink, AccentBlue)
                        )
                    )
            )
            AniFlowText(
                text = "MyListAnime",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp
            )
        }
        AniFlowText(text = "asfasdfasdgasdfa", fontSize = 10.sp)
    }
}