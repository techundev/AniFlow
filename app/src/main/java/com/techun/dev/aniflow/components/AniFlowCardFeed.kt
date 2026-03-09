package com.techun.dev.aniflow.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.techun.dev.aniflow.model.Article

@Composable
fun AniFlowCardFeed(
    modifier: Modifier = Modifier,
    article: Article,
    featured: Boolean = false,
    onClick: (Article) -> Unit,
    onToggleFav: (Int) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "card_scale"
    )

    val imageHeight = if (featured) 220.dp else 170.dp
    val tc = Color(0xFFE879F9)/*tagColor(article.tag)*/

    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(
                elevation = if (isPressed) 2.dp else 8.dp,
                shape = MaterialTheme.shapes.medium,
                ambientColor = tc.copy(alpha = 0.2f),
                spotColor = tc.copy(alpha = 0.2f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick(article) }), shape = MaterialTheme.shapes.large
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            ) {
                //Banner Card
                AniFlowAsyncImage(
                    data = article.imageUrl,
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
                    tag = article.tag,
                    color = tc,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                )

                //Fav
                AniFlowFavoriteButton(
                    isFav = article.isFavorite,
                    onToggle = { onToggleFav(article.id) },
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
                    text = article.title,
                    fontSize = if (featured) 16.sp else 13.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = if (featured) 22.sp else 18.sp,
                    maxLines = 3
                )
                AniFlowText(
                    text = article.summary,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                Spacer(modifier = Modifier.height(2.dp))
                CardFooter(
                    source = article.source,
                    timeAgo = article.timeAgo,
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
                if (isFav) AccentPink.copy(alpha = 0.2f)
                else Color.Black.copy(alpha = 0.4f)
            )
            .clickable(onClick = onToggle)
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


private val AccentPink = Color(0xFFE879F9)
private val AccentBlue = Color(0xFF38BDF8)

@Composable
private fun CardFooter(
    modifier: Modifier, source: String, timeAgo: String
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
                text = source, style = MaterialTheme.typography.labelSmall, fontSize = 10.sp
            )
        }
        AniFlowText(text = timeAgo, fontSize = 10.sp)
    }
}