package com.techun.dev.aniflow.feed.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    onNewsClick: (String) -> Unit,
    viewModel: FeedViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val isLoadingMore by viewModel.isLoadingMore.collectAsStateWithLifecycle()
    val hasMoreItems by viewModel.hasMoreItems.collectAsStateWithLifecycle()

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
                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.refresh() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        FeedContent(
                            items = state.items,
                            isLoadingMore = isLoadingMore,
                            hasMoreItems = hasMoreItems,
                            onItemClick = { newsItem -> onNewsClick(newsItem) },
                            onToggleFav = { viewModel.toggleFavorite(it) },
                            onLoadMore = { viewModel.loadNextPage() }
                        )
                    }

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
    isLoadingMore: Boolean,
    hasMoreItems: Boolean,
    onItemClick: (String) -> Unit,
    onToggleFav: (NewsItem) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember(hasMoreItems) {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem != null && lastVisibleItem.index >= totalItems - 3 && hasMoreItems
        }
    }

    LaunchedEffect(shouldLoadMore, isLoadingMore) {
        if (shouldLoadMore && !isLoadingMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            }
        }

        if (!hasMoreItems && items.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AniFlowText(
                        text = "Ya viste todo por ahora 👀",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}