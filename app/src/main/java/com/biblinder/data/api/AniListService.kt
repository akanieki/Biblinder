package com.biblinder.data.api

import retrofit2.http.*

/**
 * AniList API interface for GraphQL queries.
 * Base URL: https://graphql.anilist.co/
 */
interface AniListService {
    
    @POST("oauth/token")
    suspend fun getToken(@Body request: TokenRequest): TokenResponse

    @POST("/")
    suspend fun updateAnimeStatus(
        @Header("Authorization") token: String, 
        @Body query: String
    ): ApiResponse

    @POST("/")
    suspend fun getUserList(
        @Header("Authorization") token: String,
        @Body query: String
    ): UserAnimeResponse
}

// GraphQL işlemleri için gereken yardımcı veri modelleri
data class TokenRequest(val grant_type: String, val client_id: Int, val client_secret: String, val code: String, val redirect_uri: String)
data class TokenResponse(val access_token: String, val refresh_token: String?)
data class UserAnimeResponse(val data: UserData)
data class UserData(val MediaListCollection: MediaListCollection)
data class MediaListCollection(val lists: List<AnimeList>)
data class AnimeList(val entries: List<AnimeEntry>)
data class AnimeEntry(val media: Media, val score: Float)
data class Media(val id: Int, val title: Title, val coverImage: CoverImage)
data class Title(val english: String?, val romaji: String)
data class CoverImage(val large: String)
