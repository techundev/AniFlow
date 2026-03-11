package com.techun.dev.aniflow.feed.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.techun.dev.aniflow.core.components.AniFlowButton
import com.techun.dev.aniflow.core.components.AniFlowText
import com.techun.dev.aniflow.feed.composable.AniFlowCardFeed
import com.techun.dev.aniflow.feed.domain.model.NewsItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedScreen(viewModel: FeedViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is FeedUiState.Error -> {
                    FeedError(
                        message = state.message, onRetry = { viewModel.syncFeed() })
                }

                is FeedUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is FeedUiState.Success -> {
                    FeedContent(
                        items = state.items,
                        onItemClick = { newsItem ->

                        },
                        onToggleFav = { viewModel.toggleFavorite(it) })
                }
            }
        }
    }
}

@Composable
fun FeedError(
    message: String, onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AniFlowText(
            text = "😵 Algo salió mal", fontSize = 18.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        AniFlowText(
            text = message, fontSize = 13.sp, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        AniFlowButton(
            onclick = onRetry, text = "Reintentar"
        )
    }
}

@Composable
fun FeedContent(
    items: List<NewsItem>,
    onItemClick: (NewsItem) -> Unit,
    onToggleFav: (NewsItem) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items, key = { it.id }) { newsItem ->
            AniFlowCardFeed(
                item = newsItem,
                featured = newsItem.id == items.first().id,
                onClick = onItemClick,
                onToggleFav = onToggleFav
            )
        }
    }
}