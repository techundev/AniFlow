package com.techun.dev.aniflow.detail.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.techun.dev.aniflow.core.components.AniFlowAsyncImage
import com.techun.dev.aniflow.core.components.AniFlowButton
import com.techun.dev.aniflow.core.components.AniFlowButtonSecondary
import com.techun.dev.aniflow.core.components.AniFlowText
import com.techun.dev.aniflow.core.utils.readingTimeMinutes
import com.techun.dev.aniflow.core.utils.toReadableDate
import com.techun.dev.aniflow.detail.components.AniFlowRelatedCard
import com.techun.dev.aniflow.feed.composable.AniFlowFavoriteButton
import com.techun.dev.aniflow.feed.composable.AniFlowTagBadge
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailScreen(
    newsItemId: String,
    onRelatedClick: (String) -> Unit,
    viewModel: DetailsViewModel = koinViewModel()
) {
    LaunchedEffect(newsItemId) {
        viewModel.loadNewsItem(id = newsItemId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val related by viewModel.related.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            when (val state = uiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is DetailUiState.Success -> {
                    DetailContent(
                        newsItem = state.newsItem,
                        related = related,
                        onOpenLink = {
                            openLink(context = context, url = state.newsItem.link)
                        },
                        onShare = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, state.newsItem.title)
                                putExtra(Intent.EXTRA_TEXT, state.newsItem.link)
                            }
                            context.startActivity(Intent.createChooser(intent, "Compartir via"))
                        },
                        onToggleFav = {
                            viewModel.toggleFavorite(state.newsItem)
                        },
                        onRelatedClick = onRelatedClick
                    )
                }

                is DetailUiState.Error -> {
                    AniFlowText(
                        text = "😵 No se encontró el artículo",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

private fun openLink(context: Context, url: String) {
    if (url.isBlank()) return
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            val chooser = Intent.createChooser(intent, "Abrir con...")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        }
    } catch (e: ActivityNotFoundException) {
        Log.e("DetailScreen", "No app found to open: $url")
        Toast.makeText(context, "No se encontró una app para abrir el link", Toast.LENGTH_SHORT)
            .show()
    }
}

@Composable
fun DetailContent(
    newsItem: NewsItem,
    related: List<NewsItem>,
    onOpenLink: () -> Unit,
    onShare: () -> Unit,
    onToggleFav: () -> Unit,
    onRelatedClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AniFlowAsyncImage(
                data = newsItem.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )

            AniFlowFavoriteButton(
                isFav = newsItem.isFavorite,
                onToggle = onToggleFav,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            )
        }

        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AniFlowTagBadge(
                    tag = "MAL News", color = Color(0xFFE879F9)
                )
                AniFlowText(
                    text = newsItem.pubDate,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AniFlowText(
                text = newsItem.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp
            )

            AniFlowText(
                text = "⏱ ${newsItem.description.readingTimeMinutes()} min de lectura",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            AniFlowText(
                text = newsItem.description, fontSize = 14.sp, lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AniFlowButtonSecondary(
                    text = "Compartir", modifier = Modifier.weight(1f), onClick = onShare
                )
                AniFlowButton(
                    text = "Ver más", modifier = Modifier.weight(1f), onclick = onOpenLink
                )
            }

            if (related.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                AniFlowText(
                    text = "Noticias relacionadas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 2.dp)
                ) {
                    items(
                        items = related,
                        key = { it.id }
                    ) { relatedItem ->
                        AniFlowRelatedCard(
                            newsItem = relatedItem,
                            onClick = { onRelatedClick(it.id) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}