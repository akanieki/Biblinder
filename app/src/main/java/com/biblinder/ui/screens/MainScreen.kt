package com.biblinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biblinder.swipe.SwipeCard
import com.biblinder.swipe.SwipeViewModel
import com.biblinder.data.api.JikanRepository
import com.biblinder.data.Anime

@Composable
fun MainScreen(
    repo: JikanRepository,
    onNavigateLists: () -> Unit,
    onNavigateTournament: () -> Unit
) {
    val viewModel: SwipeViewModel = viewModel()
    var currentAnime by remember { mutableStateOf<Anime?>(null) }
    var animePool by remember { mutableStateOf(listOf<Anime>()) }

    LaunchedEffect(Unit) {
        animePool = repo.fetchMixed()
        currentAnime = animePool.firstOrNull()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        currentAnime?.let { anime ->
            SwipeCard(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel
            ) { action ->
                // handle action
                animePool = animePool.drop(1)
                currentAnime = animePool.firstOrNull()
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onNavigateLists) {
                Icon(Icons.Default.List, contentDescription = "Lists")
            }
            IconButton(onClick = onNavigateTournament) {
                Icon(Icons.Default.EmojiEvents, contentDescription = "Tournament")
            }
        }
    }
}
