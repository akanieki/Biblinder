package com.biblinder.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.biblinder.data.model.Anime

@Composable
fun AnimeCardOneHanded(
    anime: Anime,
    onRatingChange: (Float) -> Unit,
    onInfoClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedRating by remember { mutableStateOf(anime.personalScore ?: 0f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(anime.title, style = MaterialTheme.typography.titleLarge)
            Text(anime.synopsis.take(120) + "...", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "MAL: ${anime.malScore}",
                    color = MaterialTheme.colorScheme.tertiary
                )
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.Star, contentDescription = "Rate")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        for (i in 0..10) {
                            val score = i / 2f
                            DropdownMenuItem(
                                text = { Text("$score ★") },
                                onClick = {
                                    selectedRating = score
                                    expanded = false
                                    onRatingChange(selectedRating)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
