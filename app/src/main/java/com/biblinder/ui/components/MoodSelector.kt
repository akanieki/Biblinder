// Kendi paket adın en üstte kalsın (örneğin: package com.biblinder.ui)

// Sistemin "Bunlar kim?" dememesi için gereken kimlik kartları (Imports)
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MoodSelector(onMoodSelected: (String) -> Unit) {
    val moods = listOf("Need Action", "Deep Thinker", "Heart Melter", "Chill & Cozy", "Dark Mode")
    LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        items(moods) { mood ->
            AssistChip(
                onClick = { onMoodSelected(mood) },
                label = { Text(mood) },
                colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFA3E635))
            )
            Spacer(Modifier.width(8.dp))
        }
    }
}
