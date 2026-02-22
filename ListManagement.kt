package com.biblinder.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

enum class ListType {
    WATCHED,
    PLAN_TO_WATCH,
    WATCHING,
    DISCARD
}

@Entity(tableName = "anime_list_items")
data class AnimeListItem(
    @PrimaryKey val malId: Int,
    val title: String,
    val imageUrl: String,
    val synopsis: String,
    val malScore: Double?,
    val personalRating: Float,
    val defaultListType: ListType?,
    val customListId: Long?,
    val isManuallyAdded: Boolean = false,
    val addedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_lists")
data class CustomList(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdTimestamp: Long = System.currentTimeMillis()
)

class ListManagementRepository(private val dao: AnimeDao) {

    suspend fun moveToDefaultList(anime: AnimeListItem, newListType: ListType) {
        val current = dao.getAnimeById(anime.malId)
        if (current != null) {
            dao.updateAnime(current.copy(defaultListType = newListType))
        } else {
            dao.insertAnime(anime.copy(defaultListType = newListType))
        }
    }

    suspend fun moveToCustomList(anime: AnimeListItem, customListId: Long) {
        val current = dao.getAnimeById(anime.malId)
        if (current != null) {
            dao.updateAnime(current.copy(customListId = customListId))
        } else {
            dao.insertAnime(anime.copy(customListId = customListId))
        }
    }

    suspend fun removeFromDefaultList(malId: Int) {
        val current = dao.getAnimeById(malId) ?: return
        if (current.customListId == null) {
            dao.deleteById(malId)
        } else {
            dao.updateAnime(current.copy(defaultListType = null))
        }
    }

    suspend fun removeFromCustomList(malId: Int) {
        val current = dao.getAnimeById(malId) ?: return
        if (current.defaultListType == null) {
            dao.deleteById(malId)
        } else {
            dao.updateAnime(current.copy(customListId = null))
        }
    }

    suspend fun deleteCompletely(malId: Int) {
        dao.deleteById(malId)
    }
}
