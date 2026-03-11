package com.techun.dev.aniflow.favorite.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.techun.dev.aniflow.core.components.AniFlowText
import com.techun.dev.aniflow.favorite.data.mapper.toNewsItem
import com.techun.dev.aniflow.favorite.domain.model.FavoriteItem
import com.techun.dev.aniflow.home.composable.AniFlowCardFeed
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            when (val state = uiState) {
                is FavoritesUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is FavoritesUiState.Empty -> {
                    FavoritesEmpty(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is FavoritesUiState.Success -> {
                    FavoritesContent(
                        items = state.items, onRemove = { viewModel.removeFavorites(it) })
                }
            }
        }
    }
}

@Composable
fun FavoritesContent(
    items: List<FavoriteItem>, onRemove: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { favoriteItem ->
            AniFlowCardFeed(
                item = favoriteItem.toNewsItem(),
                featured = favoriteItem.id == items.first().id,
                onClick = {},
                onToggleFav = {onRemove(favoriteItem.id)}
            )
        }
    }
}

@Composable
fun FavoritesEmpty(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AniFlowText(text = "🤍", fontSize = 48.sp)
        AniFlowText(
            text = "No tienes favoritos aun", fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
        AniFlowText(
            text = "Toca ❤️ en cualquier noticia para guardarla aquí",
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}