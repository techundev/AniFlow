package com.techun.dev.aniflow.profile.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.techun.dev.aniflow.core.components.AniFlowAsyncImage
import com.techun.dev.aniflow.core.components.AniFlowText
import com.techun.dev.aniflow.profile.components.AniFlowStatCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isEditingName) {
        EditNameDialog(
            currentName = uiState.userName,
            onConfirm = { viewModel.updateUserName(it) },
            onDismiss = { viewModel.hideEditName() })
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileHeader(
                userName = uiState.userName,
                avatarUrl = uiState.avatarUrl,
                onEditName = { viewModel.showEditName() })

            Spacer(modifier = Modifier.height(24.dp))

            ProfileStatsSection(
                totalNews = uiState.totalNews, totalFavorites = uiState.totalFavorites
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileAboutSection()
        }
    }
}

@Composable
fun ProfileHeader(
    userName: String, avatarUrl: String, onEditName: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            AniFlowAsyncImage(
                data = avatarUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFE879F9), Color(0xFF38BDF8))
                        )
                    )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AniFlowText(
                text = userName, fontSize = 22.sp, fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onEditName) {
                Icon(
                    imageVector = Icons.Default.Edit, contentDescription = "Editar nombre"
                )
            }
        }
    }
}

@Composable
private fun ProfileAboutSection() {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AniFlowText(
            text = "Acerca de", fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AboutRow(label = "App", value = "AniFlow")
                AboutRow(label = "Versión", value = "1.0.0")
                AboutRow(label = "Fuente", value = "MyAnimeList RSS")
                AboutRow(label = "Autor", value = "Techundev")
            }
        }
    }
}

@Composable
private fun AboutRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AniFlowText(
            text = label, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp
        )
        AniFlowText(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ProfileStatsSection(
    totalNews: Int, totalFavorites: Int
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AniFlowText(
            text = "Estadísticas", fontSize = 16.sp, fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AniFlowStatCard(
                modifier = Modifier.weight(1f),
                label = "Noticias",
                value = totalNews.toString(),
                emoji = "📰"
            )
            AniFlowStatCard(
                modifier = Modifier.weight(1f),
                label = "Favoritos",
                value = totalFavorites.toString(),
                emoji = "❤️"
            )
        }
    }
}

@Composable
private fun EditNameDialog(
    currentName: String, onConfirm: (String) -> Unit, onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(currentName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { AniFlowText(text = "Editar nombre", fontWeight = FontWeight.Bold) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { AniFlowText(text = "Nombre") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text) }) {
                AniFlowText(text = "Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                AniFlowText(text = "Cancelar")
            }
        })
}