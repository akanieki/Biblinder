package com.biblinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.biblinder.data.local.AnimeEntity
import com.biblinder.ui.components.DropdownMenuWrapper
import com.biblinder.viewmodel.ListsViewModel

// Tema renkleri inline tanımlandı (ui/theme'den import edilemiyorsa)
private val Yellow = Color(0xFFFFC107)
private val Green = Color(0xFF4CAF50)

@Composable
fun ListsScreen(viewModel: ListsViewModel = hiltViewModel()) {
    val lists by viewModel.lists.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(lists) { anime ->
            AnimeListItem(
                anime = anime,
                onMove = { viewModel.moveAnime(it) },
                onDelete = { viewModel.deleteAnime(it) }
            )
        }
    }
}

@Composable
fun AnimeListItem(
    anime: AnimeEntity,
    onMove: (AnimeEntity) -> Unit,
    onDelete: (AnimeEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(anime.title, style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("MAL: ${anime.malScore}", color = Yellow)
                anime.personalScore?.let { Text("You: $it", color = Green) }
                DropdownMenuWrapper(anime, onMove, onDelete)
            }
        }
    }
}
