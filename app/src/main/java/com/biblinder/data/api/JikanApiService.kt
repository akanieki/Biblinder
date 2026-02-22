package com.biblinder.data.api

import com.biblinder.data.model.Anime // Az önce oluşturduğumuz ruhu buraya çağırdık
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

    // Repository'nin arayıp bulamadığı o hayati fonksiyon!
    fun toDomain() = Anime(
        id = mal_id,
        title = title,
        synopsis = synopsis ?: "No synopsis available",
        imageUrl = images.jpg.large_image_url ?: "",
        malScore = score
    )
}

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("filter") filter: String? = null,
        @Query("page") page: Int = 1
    ): JikanResponse<List<AnimeData>>

    @GET("anime")
    suspend fun getAnimeByGenre(
        @Query("genres") genreId: Int,
        @Query("limit") limit: Int = 20
    ): JikanResponse<List<AnimeData>>
}
