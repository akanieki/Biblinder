package com.biblinder.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

val Anthracite = Color(0xFF121212)
val BrolyGreen = Color(0xFFA3E635)
val Yellow = Color(0xFFFFC107)
val Green = Color(0xFF00E676)

private val DarkColorScheme = darkColorScheme(
    primary = BrolyGreen,
    background = Anthracite,
    surface = Anthracite,
    onPrimary = Color.Black,
    onBackground = Color.White
)

@Composable
fun BiblinderTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkColorScheme, content = content)
}
