package com.biblinder.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface JikanApiService {
    // Popüler ve Niş animeleri çekmek için kullanılan ana endpoint
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1, 
        @Query("filter") filter: String? = null
    ): ApiResponse

    // Mood-Based (Ruh haline göre) arama yapmak için kullanılan tür bazlı endpoint
    @GET("anime")
    suspend fun getAnimeByGenre(
        @Query("genres") genreId: Int
    ): ApiResponse
}
