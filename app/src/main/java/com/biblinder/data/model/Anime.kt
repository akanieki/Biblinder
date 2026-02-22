package com.biblinder.data.model

data class Anime(
    val id: Int,
    val title: String,
    val synopsis: String,
    val imageUrl: String?,
    val malScore: Float?,
    val personalScore: Float? = null,
    val isWatching: Boolean = false
)
