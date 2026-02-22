package com.biblinder.ui.screens

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import com.biblinder.data.api.JikanRepository
import kotlinx.coroutines.flow.*

@Composable
fun SearchScreen(repo: JikanRepository, onAddAnime: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(query) {
        snapshotFlow { query }
            .debounce(500)
            .distinctUntilChanged()
            .collect {
                if (it.isNotEmpty()) {
                    results = repo.fetchPopular().filter { a -> a.title.contains(it, true) }.map { a -> a.title }
                }
            }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Anime") },
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn {
            items(results) { title ->
                ListItem(
                    headlineContent = { Text(title) },
                    trailingContent = {
                        IconButton(onClick = { onAddAnime(title) }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                )
            }
        }
    }
}
