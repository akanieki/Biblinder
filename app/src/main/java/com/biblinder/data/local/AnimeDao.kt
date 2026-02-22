package com.biblinder.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    // AnimeEntity sorguları (eski tablo)
    @Query("SELECT * FROM anime_table")
    suspend fun getAll(): List<AnimeEntity>

    @Query("SELECT id FROM anime_table")
    suspend fun getAllIds(): List<Int>

    @Query("SELECT * FROM anime_table WHERE listType = :status")
    suspend fun getByList(status: String): List<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(anime: AnimeEntity)

    @Query("DELETE FROM anime_table WHERE id = :animeId")
    suspend fun delete(animeId: Int)

    // AnimeListItem sorguları (liste yönetimi)
    @Query("SELECT * FROM anime_list_items WHERE defaultListType = :listType")
    fun getAnimeByDefaultList(listType: ListType): Flow<List<AnimeListItem>>

    @Query("SELECT * FROM anime_list_items WHERE customListId = :customListId")
    fun getAnimeByCustomList(customListId: Long): Flow<List<AnimeListItem>>

    @Query("SELECT * FROM anime_list_items WHERE malId = :malId")
    suspend fun getAnimeById(malId: Int): AnimeListItem?

    @Query("SELECT * FROM anime_list_items")
    suspend fun getAllSeenAnime(): List<AnimeListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeListItem)

    @Update
    suspend fun updateAnime(anime: AnimeListItem)

    @Delete
    suspend fun deleteAnime(anime: AnimeListItem)

    @Query("DELETE FROM anime_list_items WHERE malId = :malId")
    suspend fun deleteById(malId: Int)

    @Query("SELECT * FROM custom_lists ORDER BY createdTimestamp DESC")
    fun getAllCustomLists(): Flow<List<CustomList>>

    @Insert
    suspend fun insertCustomList(customList: CustomList): Long

    @Delete
    suspend fun deleteCustomList(customList: CustomList)
}
