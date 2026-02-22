package com.biblinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.biblinder.tournament.TournamentManager

@Composable
fun TournamentScreen(animePool: List<String>, onWinner: (String) -> Unit) {
    val manager = remember { TournamentManager() }
    var currentBracket by remember { mutableStateOf(manager.buildBracket(animePool)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        currentBracket.matches.firstOrNull()?.let { match ->
            Text("Which do you prefer?", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(24.dp))
            Button(onClick = {
                val next = manager.nextRound(listOf(match.anime1))
                currentBracket = next
                if (next.matches.isEmpty()) onWinner(match.anime1)
            }) { Text(match.anime1) }

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                val next = manager.nextRound(listOf(match.anime2))
                currentBracket = next
                if (next.matches.isEmpty()) onWinner(match.anime2)
            }) { Text(match.anime2) }
        }
    }
}
