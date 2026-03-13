package com.techun.dev.aniflow.feed.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techun.dev.aniflow.core.components.AniFlowAsyncImage
import com.techun.dev.aniflow.core.components.AniFlowCardFooter
import com.techun.dev.aniflow.core.components.AniFlowText
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import com.techun.dev.aniflow.ui.theme.SourceCR
import com.techun.dev.aniflow.ui.theme.SourceMAL

@Composable
fun AniFlowCardFeed(
    modifier: Modifier = Modifier,
    item: NewsItem,
    featured: Boolean = false,
    onClick: (String) -> Unit,
    onToggleFav: (NewsItem) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "card_scale"
    )

    val imageHeight = if (featured) 220.dp else 170.dp
    val tc = MaterialTheme.colorScheme.onPrimaryContainer

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 2.dp else 8.dp,
                shape = MaterialTheme.shapes.medium,
                ambientColor = tc.copy(alpha = 0.2f),
                spotColor = tc.copy(alpha = 0.2f)
            ),
        shape = MaterialTheme.shapes.large,
        onClick = { onClick(item.id) },
        interactionSource = interactionSource
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            ) {
                //Banner Card
                AniFlowAsyncImage(
                    data = item.imageUrl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                //Degradado
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent, tc.copy(alpha = 0.75f)
                                ), startY = 0.4f * imageHeight.value * 3f
                            )
                        )
                )

                //Chip
                AniFlowTagBadge(
                    tag = item.source,
                    color = when (item.source) {
                        "MAL" -> SourceMAL
                        "CR" -> SourceCR
                        else -> SourceMAL
                    }, modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                )

                //Fav
                AniFlowFavoriteButton(
                    isFav = item.isFavorite,
                    onToggle = { onToggleFav(item) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
            // ── Contenido textual
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp, end = 16.dp, top = 14.dp, bottom = 16.dp
                    ), verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                AniFlowText(
                    text = item.title,
                    fontSize = if (featured) 16.sp else 13.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = if (featured) 22.sp else 18.sp,
                    maxLines = 3
                )
                AniFlowText(
                    text = item.description,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                Spacer(modifier = Modifier.height(2.dp))
                AniFlowCardFooter(
                    source = item.source,
                    timeAgo = item.pubDate,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AniFlowFavoriteButton(
    isFav: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isFav) 1.15f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "fav_scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(30.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(
                if (isFav) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                else Color.Black.copy(alpha = 0.4f)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onToggle
            )
    ) {
        AniFlowText(
            text = if (isFav) "❤️" else "🤍", fontSize = 15.sp
        )
    }
}

@Composable
fun AniFlowTagBadge(
    tag: String, color: Color, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(color.copy(alpha = 0.13f))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        AniFlowText(
            text = tag.uppercase(),
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.08.sp
        )
    }
}