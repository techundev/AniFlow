package com.techun.dev.aniflow.detail.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.techun.dev.aniflow.core.components.AniFlowText
import com.techun.dev.aniflow.feed.composable.AniFlowTagBadge
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import org.koin.compose.viewmodel.koinViewModel
import androidx.core.net.toUri

@Composable
fun DetailScreen(
    newsItemId: String, viewModel: DetailsViewModel = koinViewModel()
) {
    LaunchedEffect(newsItemId) {
        viewModel.loadNewsItem(id = newsItemId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                        onOpenLink = {
                            val intent = Intent(Intent.ACTION_VIEW, state.newsItem.link.toUri())
                            context.startActivity(intent)
                        }
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

@Composable
fun DetailContent(
    newsItem: NewsItem,
    onOpenLink: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AniFlowAsyncImage(
            data = newsItem.imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AniFlowTagBadge(
                    tag = "MAL News",
                    color = Color(0xFFE879F9)
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

            HorizontalDivider()

            AniFlowText(
                text = newsItem.description, fontSize = 14.sp, lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            AniFlowButton(
                text = "Ver en MyAnimeList",
                onclick = onOpenLink,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}