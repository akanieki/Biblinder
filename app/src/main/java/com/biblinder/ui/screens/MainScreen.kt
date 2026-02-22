package com.biblinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biblinder.swipe.SwipeCard
import com.biblinder.swipe.SwipeViewModel
import com.biblinder.data.api.JikanRepository
// Anime modelinin doğru adresi
import com.biblinder.data.model.Anime

@Composable
fun MainScreen(
    repo: JikanRepository,
    onNavigateLists: () -> Unit,
    onNavigateTournament: () -> Unit
) {
    val viewModel: SwipeViewModel = viewModel()
    
    // 'by' yerine '=' kullanarak delegasyon hatasını tamamen engelliyoruz
    val currentAnimeState = remember { mutableStateOf<Anime?>(null) }
    val animePoolState = remember { mutableStateOf(listOf<Anime>()) }

    LaunchedEffect(Unit) {
        try {
            val fetched = repo.fetchMixed()
            animePoolState.value = fetched
            currentAnimeState.value = fetched.firstOrNull()
        } catch (e: Exception) {
            // Hata yönetimi
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        currentAnimeState.value?.let { anime ->
            SwipeCard(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel
            ) { action ->
                if (animePoolState.value.isNotEmpty()) {
                    val newList = animePoolState.value.drop(1)
                    animePoolState.value = newList
                    currentAnimeState.value = newList.firstOrNull()
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
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
