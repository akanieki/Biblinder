package com.biblinder.data.model

data class Anime(
    val id: Int,
    val title: String,
    val synopsis: String,
    val imageUrl: String,
    val malScore: Double?,
    val isWatching: Boolean = false
)
