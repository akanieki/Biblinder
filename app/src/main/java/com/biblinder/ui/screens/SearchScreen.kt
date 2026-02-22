package com.biblinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            .collect { q ->
                if (q.isNotEmpty()) {
                    results = repo.fetchPopular()
                        .filter { a -> a.title.contains(q, ignoreCase = true) }
                        .map { a -> a.title }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
