package com.biblinder.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.biblinder.data.model.Anime

@Composable
fun WinnerShareCard(anime: Anime) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF121212)) // Anthracite
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = anime.imageUrl, 
                contentDescription = anime.title,
                modifier = Modifier.height(200.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "🏆 ${anime.title}", 
                color = Color(0xFFA3E635), // BrolyGreen
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Kupa Galibi", 
                color = Color.White
            )
        }
    }
}
