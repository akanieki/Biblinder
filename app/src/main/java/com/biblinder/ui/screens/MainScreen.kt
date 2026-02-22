package com.biblinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.biblinder.swipe.SwipeCard
import com.biblinder.viewmodel.MainViewModel
import com.biblinder.data.model.Anime

// EmojiEvents extended icons kütüphanesinde — bulunamazsa fallback icon kullanıyoruz
import androidx.compose.material.icons.filled.EmojiEvents

@Composable
fun MainScreen(
    onNavigateLists: () -> Unit,
    onNavigateTournament: () -> Unit,
    viewModel: MainViewModel
) {
    val animePool by viewModel.animePool.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val currentAnime = animePool.getOrNull(currentIndex)

    LaunchedEffect(Unit) {
        viewModel.loadInitialPool()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (currentAnime != null) {
            SwipeCard(
                modifier = Modifier.fillMaxSize(),
                viewModel = com.biblinder.swipe.SwipeViewModel()
            ) { action ->
                viewModel.onSwipe(action)
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
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
