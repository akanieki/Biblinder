package com.biblinder.data.api

import retrofit2.http.*
import retrofit2.Response

/**
 * MyAnimeList API interface – OAuth2 authenticated.
 * Base URL example: https://api.myanimelist.net/v2/
 */
interface MalApiService {

    @GET("users/@me/animelist")
    suspend fun getUserList(
        @Header("Authorization") accessToken: String,
        @Query("fields") fields: String = "id,title,main_picture,status,list_status{status,score,updated_at}"
    ): MalUserListResponse

    @PUT("anime/{id}/my_list_status")
    suspend fun updateAnimeStatus(
        @Header("Authorization") accessToken: String,
        @Path("id") animeId: Int,
        @Query("status") status: String,
        @Query("score") score: Int? = null
    ): Response<Unit>
}

/** Simplified data model response for import. */
data class MalUserListResponse(
    val data: List<MalUserListItem>
)

data class MalUserListItem(
    val node: MalAnimeNode,
    val list_status: MalListStatus
)

data class MalAnimeNode(
    val id: Int,
    val title: String,
    val main_picture: MalPicture?
)

data class MalPicture(val large: String)
data class MalListStatus(val status: String, val score: Int?)
