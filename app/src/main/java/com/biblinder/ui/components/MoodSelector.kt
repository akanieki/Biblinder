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
