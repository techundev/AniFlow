package com.techun.dev.aniflow.favorite.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techun.dev.aniflow.core.components.AniFlowAsyncImage
import com.techun.dev.aniflow.core.components.AniFlowCardFooter
import com.techun.dev.aniflow.core.components.AniFlowText
import com.techun.dev.aniflow.feed.domain.model.NewsItem

@Composable
fun AniFlowCardFavorites(
    modifier: Modifier = Modifier,
    item: NewsItem,
    featured: Boolean = false,
    onClick: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var columnHeightPx by remember { mutableIntStateOf(0) }
    val isPressed by interactionSource.collectIsPressedAsState()
    val density = LocalDensity.current

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "card_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { onClick(item.id) },
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            AniFlowAsyncImage(
                data = item.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(
                        with(density) { columnHeightPx.toDp() })
                    .clip(
                        RoundedCornerShape(
                            topStart = MaterialTheme.shapes.medium.topStart,
                            bottomStart = MaterialTheme.shapes.medium.bottomStart,
                            topEnd = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 120.dp)
                    .onGloballyPositioned { columnHeightPx = it.size.height }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)) {
                AniFlowText(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (featured) 15.sp else 13.sp,
                    lineHeight = if (featured) 22.sp else 18.sp,
                    maxLines = 3
                )
                AniFlowText(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 17.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                AniFlowCardFooter(
                    timeAgo = item.pubDate, modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}