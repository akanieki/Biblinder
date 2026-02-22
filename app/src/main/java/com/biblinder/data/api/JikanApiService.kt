package com.biblinder.data.api

import com.biblinder.data.model.Anime
import retrofit2.http.GET
import retrofit2.http.Query

data class JikanResponse<T>(val data: T)

data class AnimeData(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val score: Double?,
    val images: Images
) {
    data class Images(val jpg: Jpg)
    data class Jpg(val large_image_url: String?)

    fun toDomain() = Anime(
        id = mal_id,
        title = title,
        synopsis = synopsis ?: "No synopsis available",
        imageUrl = images.jpg.large_image_url ?: "",
        malScore = score
    )
}

interface JikanApiService {
    // Popüler ve Niş animeleri çekmek için kullanılan ana endpoint (Senin eklediğin filter ile)
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("filter") filter: String? = null
    ): JikanResponse<List<AnimeData>> // ApiResponse yerine projedeki doğru model bağlandı

    // Mood-Based (Ruh haline göre) arama yapmak için kullanılan tür bazlı endpoint
    @GET("anime")
    suspend fun getAnimeByGenre(
        @Query("genres") genreId: Int
    ): JikanResponse<List<AnimeData>> // ApiResponse yerine projedeki doğru model bağlandı
}
