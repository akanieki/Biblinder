package com.biblinder.data.local

import androidx.room.*

@Dao
interface AnimeDao {
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
}
