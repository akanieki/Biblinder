package com.biblinder.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.biblinder.data.local.AnimeEntity

@Composable
fun DropdownMenuWrapper(
    anime: AnimeEntity,
    onMove: (AnimeEntity) -> Unit,
    onDelete: (AnimeEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.CenterEnd)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
                tint = Color(0xFFA3E635) // BrolyGreen
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color(0xFF121212) // Anthracite
        ) {
            DropdownMenuItem(
                text = { Text("Move to...", color = Color(0xFFA3E635)) },
                onClick = {
                    expanded = false
                    onMove(anime)
                }
            )
            DropdownMenuItem(
                text = { Text("Delete", color = Color.Red.copy(alpha = 0.9f)) },
                onClick = {
                    expanded = false
                    onDelete(anime)
                }
            )
        }
    }
}
