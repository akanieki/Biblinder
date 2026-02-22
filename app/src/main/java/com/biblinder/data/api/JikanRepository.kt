package com.biblinder.data.api

import com.biblinder.data.model.Anime
import com.biblinder.data.local.AnimeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JikanRepository @Inject constructor(
    private val api: JikanApiService,
    private val dao: AnimeDao
) {
    suspend fun fetchMixed(): List<Anime> = withContext(Dispatchers.IO) {
        val response = api.getTopAnime()
        response.data.map { it.toDomain() }.shuffled()
    }

    suspend fun fetchMoodBased(mood: String): List<Anime> = withContext(Dispatchers.IO) {
        val genreId = when (mood) {
            "Need Action" -> 1
            "Deep Thinker" -> 40
            "Heart Melter" -> 22
            "Chill & Cozy" -> 36
            "Dark Mode" -> 14
            else -> 1
        }
        val response = api.getAnimeByGenre(genreId)
        val localIds = dao.getAllIds()
        response.data.map { it.toDomain() }.filterNot { it.id in localIds }.shuffled()
    }
}
