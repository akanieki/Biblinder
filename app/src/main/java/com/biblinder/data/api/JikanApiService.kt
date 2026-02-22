package com.biblinder.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface JikanApiService {
    // Şimdilik KSP derlemesini geçmek ve sistemi ayağa kaldırmak için temel bir API çağrısı
    @GET("top/anime")
    suspend fun getTopAnime(): Any 
}
