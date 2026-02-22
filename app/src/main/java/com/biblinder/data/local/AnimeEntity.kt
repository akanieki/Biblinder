package com.biblinder.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val synopsis: String?,
    val imageUrl: String?,
    val malScore: Float,
    val personalScore: Float?,
    val listType: String // "WATCHING", "PLAN_TO_WATCH", "COMPLETED", "DROPPED"
)
